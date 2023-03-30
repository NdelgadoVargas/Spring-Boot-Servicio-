package com.pruebatecnica.servicioavlachile.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pruebatecnica.dto.UserDTO;
import com.pruebatecnica.dto.Response.CreateUserResponse;
import com.pruebatecnica.servicioavlachile.entity.UserEntity;
import com.pruebatecnica.servicioavlachile.services.UserService;
import com.pruebatecnica.utils.Token.TokenMananger;
import com.pruebatecnica.utils.message.Message;
import com.pruebatecnica.utils.validation.Validation;

@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    UserService userService;
    Validation validation = new Validation();

    @PostMapping("/create")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody UserDTO userDTO) {

        try {
            TokenMananger Encript = new TokenMananger();

            if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
                return new ResponseEntity<>(
                new CreateUserResponse(0, null, null, Message.EMAIL_REQUIRED, HttpStatus.BAD_REQUEST, null),
                HttpStatus.BAD_REQUEST);
            }

            if (!validation.emailFormat(userDTO.getEmail())) {
                return new ResponseEntity<>(new CreateUserResponse(0, null, null, Message.INVALID_EMAIL_FORMAT,
                HttpStatus.BAD_REQUEST, null), HttpStatus.BAD_REQUEST);
            }

            if (!validation.passwordFormat(userDTO.getPassword())) {
                return new ResponseEntity<>(new CreateUserResponse(0, null, null, Message.INVALID_PASSWORD_FORMAT,
                HttpStatus.BAD_REQUEST, null), HttpStatus.BAD_REQUEST);
            }

            if (userService.emailExists(userDTO.getEmail())) {
                return new ResponseEntity<>(new CreateUserResponse(0, null, null, Message.EMAIL_ALREADY_REGISTERED,
                HttpStatus.CONFLICT, null), HttpStatus.CONFLICT);
            }

            UserEntity newUser = userService.createUser(userDTO);

            if (newUser != null) {

                String token = Encript.generateToken(newUser);

                Date creationDate = newUser.getCreationDate();
                SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
                String creationDateFormat = formato.format(creationDate);

                return new ResponseEntity<>(new CreateUserResponse(newUser.getId(), creationDateFormat, null,
                Message.USER_CREATED_SUCCESSFULLY, HttpStatus.CREATED, token), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(new CreateUserResponse(0, null, null, Message.ERROR_CREATING_USER,
                HttpStatus.INTERNAL_SERVER_ERROR, null), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(new CreateUserResponse(0, null, null,
            Message.ERROR_CREATING_USER_EXCEPTION + " " + e.getMessage(), HttpStatus.BAD_GATEWAY, null),
            HttpStatus.BAD_GATEWAY);
        }

    }

}
