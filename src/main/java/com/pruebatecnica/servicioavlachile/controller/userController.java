package com.pruebatecnica.servicioavlachile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pruebatecnica.dto.PhoneDTO;
import com.pruebatecnica.dto.UserDTO;
import com.pruebatecnica.dto.UserUpdateDTO;
import com.pruebatecnica.dto.Response.CreateUserResponse;
import com.pruebatecnica.dto.Response.SearchUserResponse;
import com.pruebatecnica.dto.Response.UpdateUserResponse;
import com.pruebatecnica.dto.Response.ValidationTokenUserResponse;
import com.pruebatecnica.servicioavlachile.entity.UserEntity;
import com.pruebatecnica.servicioavlachile.entity.UserTokenEntity;
import com.pruebatecnica.servicioavlachile.repositories.UserTokenRepository;
import com.pruebatecnica.servicioavlachile.repositories.UsersRepository;
import com.pruebatecnica.servicioavlachile.services.UserService;
import com.pruebatecnica.utils.action.ActionParameterized;
import com.pruebatecnica.utils.format.DateFormat;
import com.pruebatecnica.utils.message.Message;
import com.pruebatecnica.utils.token.GenerateTokenJwt;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@Transactional
@RequestMapping("/user")
public class userController {

    @Autowired
    UserService userService;
    @Autowired
    UsersRepository userRepository;
    @Autowired
    UserTokenRepository userTokenRepository;

    ActionParameterized action = new ActionParameterized();
    DateFormat dateUtils = new DateFormat();

    private static final Logger logger = LoggerFactory.getLogger(userController.class);

    @PostMapping("/create")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody UserDTO userDTO) {

        logger.info("METODO ==> createUser");
        logger.info("RequestBody ==>"
            + "Name ==> " + userDTO.getName() + " "
            + "Email ==> " + userDTO.getEmail() + " "
            + "Password ==> " + userDTO.getPassword() + " "
        );

        for (PhoneDTO phone : userDTO.getPhones()) {
            logger.info("Phone ==>" + phone.getNumber());
        }

        try {

            GenerateTokenJwt Encript = new GenerateTokenJwt();

            ResponseEntity<CreateUserResponse> validationRequestBody = userService.validationRequestBody(userDTO);

            if (validationRequestBody.getStatusCode() == HttpStatus.OK) {

                UserEntity newUser = userService.createUser(userDTO);

                if (newUser != null) {

                    String token = Encript.generateToken(newUser.getEmail(), newUser.getPassword());

                    UserTokenEntity userToken = new UserTokenEntity();
                    userToken.setToken(token);
                    userToken.setUser(newUser);
                    userTokenRepository.save(userToken);

                    String creationDateFormat = dateUtils.dateFormat(newUser.getCreationDate());

                    return new ResponseEntity<>(new CreateUserResponse(
                        newUser.getId(), 
                        creationDateFormat, 
                        null,
                        Message.USER_CREATED_SUCCESSFULLY, 
                        HttpStatus.CREATED, 
                        token), 
                        HttpStatus.CREATED
                    );

                } else {

                    logger.warn(Message.ERROR_CREATING_USER);

                    return new ResponseEntity<>(new CreateUserResponse(
                        0, 
                        null, 
                        null, 
                        Message.ERROR_CREATING_USER,
                        HttpStatus.INTERNAL_SERVER_ERROR, 
                        null), 
                        HttpStatus.INTERNAL_SERVER_ERROR
                    );
                }

            } else {

                logger.warn(validationRequestBody.getBody().getMessage());

                CreateUserResponse createUserResponse = validationRequestBody.getBody();
                return new ResponseEntity<>(createUserResponse, 
                    HttpStatus.INTERNAL_SERVER_ERROR
                );
            }

        } catch (Exception e) {

            logger.error(Message.ERROR_CREATING_USER_EXCEPTION, e.getMessage());

            return new ResponseEntity<>(new CreateUserResponse(
                0,
                null,
                null,
                Message.ERROR_CREATING_USER_EXCEPTION + " " + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                null),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

    }

    @GetMapping("/search/{id}")
    public ResponseEntity<SearchUserResponse> getUser(
            @RequestHeader("Authorization") String token,
            @PathVariable("id") int id) {

            logger.info("METODO ==> searchUser");
            logger.info("==> "
                + "Token ==> " + token + " "
                + "Id  ==> " + id + " "
            );

        try {

            ValidationTokenUserResponse validationTokenUser = userService.getUserFromToken(token, id, action.SEARCH_USER);

            if (validationTokenUser.getStatus() == HttpStatus.OK) {

                return new ResponseEntity<>(new SearchUserResponse(
                    validationTokenUser.getMessage(),
                    validationTokenUser.getUser(),
                    validationTokenUser.getStatus()),
                    validationTokenUser.getStatus()
                );

            } else {

                logger.warn(validationTokenUser.getMessage());

                return new ResponseEntity<>(new SearchUserResponse(
                    validationTokenUser.getMessage(),
                    validationTokenUser.getUser(),
                    validationTokenUser.getStatus()),
                    validationTokenUser.getStatus()
                );

            }

        } catch (Exception e) {

            logger.error(Message.ERROR_SEARCH_USER_EXCEPTION, e.getMessage());

            return new ResponseEntity<>(new SearchUserResponse(
                Message.ERROR_SEARCH_USER_EXCEPTION + " " + e.getMessage(),
                null,
                HttpStatus.INTERNAL_SERVER_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<SearchUserResponse> deleteUser(
            @RequestHeader("Authorization") String token,
            @PathVariable("id") int id) {

            logger.info("METODO ==> deleteUser");
            logger.info("==> "
                + "Token ==> " + token + " "
                + "Id  ==> " + id + " "
            );

        try {

            ValidationTokenUserResponse validationTokenUser = userService.getUserFromToken(token, id,
                    action.DELETE_USER);

            if (validationTokenUser.getStatus() == HttpStatus.OK) {

                userService.deleteUser(id);

                return new ResponseEntity<>(new SearchUserResponse(
                    validationTokenUser.getMessage(),
                    validationTokenUser.getUser(),
                    validationTokenUser.getStatus()),
                    validationTokenUser.getStatus()
                );

            } else {

                logger.warn(validationTokenUser.getMessage());

                return new ResponseEntity<>(new SearchUserResponse(
                    validationTokenUser.getMessage(),
                    validationTokenUser.getUser(),
                    validationTokenUser.getStatus()),
                    validationTokenUser.getStatus()
                );
            }

        } catch (Exception e) {

            logger.error(Message.ERROR_DELETE_USER_EXCEPTION, e.getMessage());

            return new ResponseEntity<>(new SearchUserResponse(
                Message.ERROR_DELETE_USER_EXCEPTION + " " + e.getMessage(),
                null,
                HttpStatus.INTERNAL_SERVER_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UpdateUserResponse> updateUser(
            @RequestHeader("Authorization") String token,
            @PathVariable("id") int id,
            @RequestBody UserDTO dataUpdate) {

            logger.info("METODO ==> updateUser");
            logger.info("==> "
                + "Token ==> " + token + " "
                + "Id  ==> " + id + " "
                + "RequestBody ==> " + " "
                + "Name ==> " + dataUpdate.getName() + " "
                + "Email ==> " + dataUpdate.getEmail() + " "
                + "Password ==> " + dataUpdate.getPassword() + " "
            );

        try {

            ResponseEntity<CreateUserResponse> validationRequestBody = userService.validationRequestBody(dataUpdate);

            if (validationRequestBody.getStatusCode() == HttpStatus.OK) {

                ValidationTokenUserResponse validationTokenUser = userService.getUserFromToken(token, id,
                        action.UPDATE_USER);

                if (validationTokenUser.getStatus() == HttpStatus.OK) {

                    UpdateUserResponse updateUser = userService.updateUser(id, dataUpdate, token);

                    return ResponseEntity.ok()
                    .body(new UpdateUserResponse(
                        updateUser.getId(),
                        updateUser.getMessage(),
                        updateUser.getCreated(),
                        updateUser.getModified(),
                        updateUser.getUpdateToken(),
                        updateUser.getHttpStatus())
                    );

                } else {

                    logger.warn(validationTokenUser.getMessage());

                    return new ResponseEntity<>(new UpdateUserResponse(
                        0,
                        validationTokenUser.getMessage(),
                        null,
                        null,
                        null,
                        validationTokenUser.getStatus()),
                        validationTokenUser.getStatus()
                    );

                }
            } else {

                logger.warn(validationRequestBody.getBody().getMessage());

                CreateUserResponse createUserResponse = validationRequestBody.getBody();
                return new ResponseEntity<>(new UpdateUserResponse(
                    createUserResponse.getId(),
                    createUserResponse.getMessage(),
                    createUserResponse.getCreated(),
                    createUserResponse.getModified(),
                    createUserResponse.getToken(),
                    createUserResponse.getHttpStatus()), createUserResponse.getHttpStatus()
                );

            }

        } catch (Exception e) {

            logger.error(Message.ERROR_UPDATE_USER_EXCEPTION, e.getMessage());

            return ResponseEntity.status(
                HttpStatus.INTERNAL_SERVER_ERROR)
            .build();
        }

    }

}
