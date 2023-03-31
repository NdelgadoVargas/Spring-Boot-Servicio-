package com.pruebatecnica.servicioavlachile.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.pruebatecnica.dto.UserDTO;
import com.pruebatecnica.dto.Response.ValidationTokenUserResponse;
import com.pruebatecnica.servicioavlachile.entity.UserEntity;
import com.pruebatecnica.servicioavlachile.entity.UserTokenEntity;
import com.pruebatecnica.servicioavlachile.repositories.UserTokenRepository;
import com.pruebatecnica.servicioavlachile.repositories.UsersRepository;
import com.pruebatecnica.utils.message.Message;
import com.pruebatecnica.utils.message.MessageException;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class UserService {

    @Autowired
    UsersRepository userRepository;
    @Autowired
    UserTokenRepository userTokenRepository;
    
    public UserEntity createUser(UserDTO userDTO) {

        if (userDTO.getName() == null || userDTO.getName().isEmpty() ||
                userDTO.getEmail() == null || userDTO.getEmail().isEmpty() ||
                userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {

            throw new IllegalArgumentException("Nombre,email y password son campos requeridos");
        }

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


    public ValidationTokenUserResponse getUserFromToken(String token,int id) {
        // RETORNAR UN MENSAJE ERROR - RETORNAR DATA DE USER - HTTP STATUS
        
        try {
            
            String tokenValue = token.replaceFirst("Bearer ", "");
            UserTokenEntity userToken = userTokenRepository.findByToken(tokenValue);
    
            UserEntity dataUser = userRepository.findById(id).orElse(null);
    
            if (userToken == null) {
                return new ValidationTokenUserResponse( Message.ERROR_INVALID_TOKEN, null, HttpStatus.UNAUTHORIZED);
            }
            
            if (dataUser == null) {
                return new ValidationTokenUserResponse( Message.ERROR_USER_NOT_FOUND, null, HttpStatus.UNAUTHORIZED);
            }

            if (userToken.getId() != dataUser.getId()) {
                return new ValidationTokenUserResponse( Message.ERROR_INVALID_TOKEN, null, HttpStatus.UNAUTHORIZED);
            }
    
    
            return new ValidationTokenUserResponse( Message.ERROR_INVALID_TOKEN, dataUser, HttpStatus.OK);

        } catch (Exception e) {

            return new ValidationTokenUserResponse( Message.ERROR_INVALID_TOKEN+" "+ e.getMessage(), null,  HttpStatus.INTERNAL_SERVER_ERROR);

        }
       

    }

    
}
