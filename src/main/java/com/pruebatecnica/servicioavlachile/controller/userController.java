package com.pruebatecnica.servicioavlachile.controller;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.pruebatecnica.utils.Token.generateTokenJwt;
import com.pruebatecnica.utils.action.ActionParameterized;
import com.pruebatecnica.utils.message.Message;
import com.pruebatecnica.utils.validation.Validation;
import jakarta.transaction.Transactional;
// FALTA IMPLEMENTAR LOGG
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

    @PostMapping("/create")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody UserDTO userDTO) {

        try {
            Validation validation = new Validation();
            generateTokenJwt Encript = new generateTokenJwt();

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

                String token = Encript.generateToken(newUser.getEmail(),newUser.getPassword());

                UserTokenEntity userToken = new UserTokenEntity();
                userToken.setToken(token);
                userToken.setUser(newUser);
                userTokenRepository.save(userToken);

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

    @GetMapping("/search/{id}")
    public ResponseEntity<SearchUserResponse> getUser(@RequestHeader("Authorization") String token, @PathVariable("id") int id){

        try {

            ValidationTokenUserResponse validationTokenUser = userService.getUserFromToken(token,id, action.SEARCH_USER);

            return new ResponseEntity<>(new SearchUserResponse(
                validationTokenUser.getMessage(), 
                validationTokenUser.getUser(),  
                validationTokenUser.getStatus()),  
                validationTokenUser.getStatus()
            );

        } catch (Exception e) {
            return new ResponseEntity<>(new SearchUserResponse( 
                Message.ERROR_CREATING_USER_EXCEPTION + " " + e.getMessage(),
                null,
                HttpStatus.INTERNAL_SERVER_ERROR), 
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
       
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<SearchUserResponse> deleteUser(@RequestHeader("Authorization") String token, @PathVariable("id") int id){

        try {

            ValidationTokenUserResponse validationTokenUser = userService.getUserFromToken(token,id, action.DELETE_USER);
            
            if(validationTokenUser.getStatus() == HttpStatus.OK){
                userService.deleteUser(id);
            }
           
            return new ResponseEntity<>(new SearchUserResponse( 
                validationTokenUser.getMessage(), 
                validationTokenUser.getUser(),
                validationTokenUser.getStatus()), 
                validationTokenUser.getStatus()
            );

        } catch (Exception e) {
            return new ResponseEntity<>(new SearchUserResponse(
                Message.ERROR_CREATING_USER_EXCEPTION + " " + e.getMessage(), 
                null, 
                HttpStatus.INTERNAL_SERVER_ERROR), 
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<UpdateUserResponse> updateUser 
        (
            @RequestHeader("Authorization") String token,
            @PathVariable("id") int id, 
            @RequestBody UserUpdateDTO dataUpdate)
        {

        try {

            ValidationTokenUserResponse validationTokenUser = userService.getUserFromToken( token, id, action.UPDATE_USER);
    
            if(validationTokenUser.getStatus() == HttpStatus.OK){

                UpdateUserResponse updateUser =  userService.updateUser(id, dataUpdate);
                
                return ResponseEntity.ok()
                .body(new UpdateUserResponse(
                        updateUser.getId(),
                        updateUser.getMessage(),
                        updateUser.getCreated(),
                        updateUser.getModified(),
                        updateUser.getHttpStatus()
                ));

            }else{

                return new ResponseEntity<>(new UpdateUserResponse(
                    0,
                    validationTokenUser.getMessage(), 
                    null,
                    null,
                    validationTokenUser.getStatus()),
                    validationTokenUser.getStatus()
                );

            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
    
    


}
