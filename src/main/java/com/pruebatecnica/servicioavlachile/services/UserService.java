package com.pruebatecnica.servicioavlachile.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.pruebatecnica.dto.UserDTO;
import com.pruebatecnica.dto.UserUpdateDTO;
import com.pruebatecnica.dto.Response.UpdateUserResponse;
import com.pruebatecnica.dto.Response.ValidationTokenUserResponse;
import com.pruebatecnica.servicioavlachile.entity.UserEntity;
import com.pruebatecnica.servicioavlachile.entity.UserTokenEntity;
import com.pruebatecnica.servicioavlachile.repositories.UserTokenRepository;
import com.pruebatecnica.servicioavlachile.repositories.UsersRepository;
import com.pruebatecnica.utils.date.DateFormat;
import com.pruebatecnica.utils.message.Message;
import com.pruebatecnica.utils.token.GenerateTokenJwt;
import jakarta.transaction.Transactional;


@Service
@Transactional
public class UserService {

    @Autowired
    UsersRepository userRepository;
    @Autowired
    UserTokenRepository userTokenRepository;

    DateFormat dateUtils = new DateFormat();

    public UserEntity createUser(UserDTO userDTO) {

        return userRepository.save(userDTO.toEntity());

    }

    public boolean emailExists(String email) {

        return userRepository.findByEmail(email).isPresent();

    }

    public UserEntity getUser(int id) {

        return userRepository.findById(id).orElse(null);

    }

    public Boolean deleteUser(int id) {
        try {

            userRepository.deleteById(id);
            return true;

        } catch (EmptyResultDataAccessException e) {

            return false;
        }
    }


    public ValidationTokenUserResponse getUserFromToken(String token,int id, String action) {
        
        try {

            UserEntity user = userRepository.findById(id).orElse(null);
            String tokenValue = token.replaceFirst("Bearer ", "");
            UserTokenEntity userToken = userTokenRepository.findByToken(tokenValue);
            long token_idUser, IdUser;
           

            if (user == null) {
                return new ValidationTokenUserResponse( Message.ERROR_USER_NOT_FOUND, null, HttpStatus.UNAUTHORIZED);
            }
     
            if (userToken == null) {
                return new ValidationTokenUserResponse( Message.ERROR_INVALID_TOKEN, null, HttpStatus.UNAUTHORIZED);
            }
           
            token_idUser = userToken.getUser().getId();
            IdUser = user.getUserToken().getUser().getId();

            if (token_idUser != IdUser) {
                return new ValidationTokenUserResponse( Message.ERROR_INVALID_TOKEN, null, HttpStatus.UNAUTHORIZED);
            }

            switch (action) {

                case "SEARCH_USER":
                return new ValidationTokenUserResponse( Message.USER_FOUND_SUCCESS, user, HttpStatus.OK);

                case "DELETE_USER":
                return new ValidationTokenUserResponse( Message.USER_DELETE_SUCCESS, null, HttpStatus.OK);

                case "UPDATE_USER":
                return new ValidationTokenUserResponse( null,null,HttpStatus.OK);
            
                default:
                return new ValidationTokenUserResponse( null,null,null);
            }

        } catch (Exception e) {

            return new ValidationTokenUserResponse( Message.ERROR_INVALID_TOKEN+" "+ e.getMessage(), null,  HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


    public UpdateUserResponse updateUser(int id , UserUpdateDTO dataUpdate, String token) {


        try {
            
            GenerateTokenJwt generateTokenJwt = new GenerateTokenJwt();

            UserEntity user =  userRepository.findById(id).orElse(null);
    
            String oldToken = token.replaceFirst("Bearer ", "");
            UserTokenEntity currentToken = userTokenRepository.findByToken(oldToken);
    
            long token_idUser, IdUser;
            token_idUser = currentToken.getUser().getId();
            IdUser = user.getUserToken().getUser().getId();
    
            if( token_idUser == IdUser){
    
                // ACTUALIZO USER
                user.setName(dataUpdate.getName());
                user.setEmail(dataUpdate.getEmail());
                user.setPassword(dataUpdate.getPassword());
    
                String newToken = generateTokenJwt.generateToken(user.getEmail(), user.getPassword());
               
                String creationDateFormat = dateUtils.dateFormat(user.getCreationDate());
                String modifiedDateFormat = dateUtils.dateFormat(user.getCreationDate());
    
                // ACTUALIZO TOKEN
                currentToken.setToken(newToken);
                currentToken.setUser(user);
    
                return new UpdateUserResponse(
                    user.getId(),
                    Message.USER_UPDATE_SUCCESS, 
                    creationDateFormat, 
                    modifiedDateFormat,
                    newToken, 
                    HttpStatus.OK
                );
    
            }else{
    
                return new UpdateUserResponse(
                    0,
                    Message.ERROR_INVALID_TOKEN, 
                    null, 
                    null,
                    null, 
                    HttpStatus.UNAUTHORIZED
                );
    
            }

        } catch (Exception e) {
           
            return new UpdateUserResponse(
                    0,
                    Message.ERROR_CREATING_USER_EXCEPTION+" "+ e.getMessage(), 
                    null, 
                    null,
                    null, 
                    HttpStatus.INTERNAL_SERVER_ERROR
                );
        }

        
        
        
    }
    
}
