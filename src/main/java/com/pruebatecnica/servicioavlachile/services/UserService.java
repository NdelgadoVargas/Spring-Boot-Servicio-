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

import aj.org.objectweb.asm.Type;
import ch.qos.logback.core.joran.action.Action;
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


    public ValidationTokenUserResponse getUserFromToken(String token,int id, String action) {
        
        try {
            
            String tokenValue = token.replaceFirst("Bearer ", "");
            UserTokenEntity userToken = userTokenRepository.findByToken(tokenValue);
    
            UserEntity user = userRepository.findById(id).orElse(null);
    
            if (user == null) {
                return new ValidationTokenUserResponse( Message.ERROR_USER_NOT_FOUND, null, HttpStatus.UNAUTHORIZED);
            }

            if (userToken == null) {
                return new ValidationTokenUserResponse( Message.ERROR_INVALID_TOKEN, null, HttpStatus.UNAUTHORIZED);
            }

            if (userToken.getId() != user.getId()) {
                return new ValidationTokenUserResponse( Message.ERROR_INVALID_TOKEN, null, HttpStatus.UNAUTHORIZED);
            }

            switch (action) {

                case "SEARCH_USER":
                return new ValidationTokenUserResponse( Message.USER_FOUND_SUCCESS, user, HttpStatus.OK);

                case "DELETE_USER":
                return new ValidationTokenUserResponse( Message.USER_DELETE_SUCCESS, null, HttpStatus.OK);

                case "UPDATE_USER":
                return new ValidationTokenUserResponse( Message.USER_UPDATE_SUCCESS, user, HttpStatus.OK);
            
                default:
                return new ValidationTokenUserResponse( null,null,null);
            }

        } catch (Exception e) {

            return new ValidationTokenUserResponse( Message.ERROR_INVALID_TOKEN+" "+ e.getMessage(), null,  HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


    // public UserEntity updateUser(int id) {
    //     return userRepository.update(id).orElse(null);
    // }
    
}
