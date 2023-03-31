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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pruebatecnica.dto.UserDTO;
import com.pruebatecnica.dto.Response.CreateUserResponse;
import com.pruebatecnica.dto.Response.SearchUserResponse;
import com.pruebatecnica.servicioavlachile.entity.UserEntity;
import com.pruebatecnica.servicioavlachile.entity.UserTokenEntity;
import com.pruebatecnica.servicioavlachile.repositories.UserTokenRepository;
import com.pruebatecnica.servicioavlachile.repositories.UsersRepository;
import com.pruebatecnica.servicioavlachile.services.UserService;
import com.pruebatecnica.utils.Token.PersistToken;
import com.pruebatecnica.utils.message.Message;
import com.pruebatecnica.utils.validation.Validation;
import jakarta.transaction.Transactional;


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

    @PostMapping("/create")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody UserDTO userDTO) {

        try {
            Validation validation = new Validation();
            PersistToken Encript = new PersistToken();

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

                String token = Encript.generateToke(newUser.getEmail(),newUser.getPassword());

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

    // OBTENER GET 
    @GetMapping("/search/{id}")
    public ResponseEntity<SearchUserResponse> getUser(@RequestHeader("Authorization") String token, @PathVariable("id") int id){

        try {

            String tokenValue = token.replaceFirst("Bearer ", "");
            UserTokenEntity userToken = userTokenRepository.findByToken(tokenValue);
            if (userToken == null) {
                return new ResponseEntity<>(new SearchUserResponse( Message.ERROR_INVALID_TOKEN, null, HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
            }

            UserEntity dataUser = userService.getUser(id);

            if(dataUser == null){
                return new ResponseEntity<>(new SearchUserResponse( Message.ERROR_USER_NOT_FOUND, null, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(new SearchUserResponse( Message.USER_FOUND_SUCCESS, dataUser, HttpStatus.OK), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new SearchUserResponse( Message.ERROR_CREATING_USER_EXCEPTION + " " + e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
       
    }



    // DELETE DELETE
    // @DeleteMapping("/delete/{id}")
    // public ResponseEntity<SearchUserResponse> deleteUser(@RequestHeader("Authorization") String token, @PathVariable("id") int id){

    //     try {

    //         String tokenValue = token.replaceFirst("Bearer ", "");
    //         UserTokenEntity userToken = userTokenRepository.findByToken(tokenValue);
    //         if (userToken == null) {
    //             return new ResponseEntity<>(new SearchUserResponse( Message.ERROR_INVALID_TOKEN, null, HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
    //         }

    //         UserEntity dataUser = userService.deleteUser(id);

    //         if(dataUser == null){
    //             return new ResponseEntity<>(new SearchUserResponse( Message.ERROR_USER_NOT_FOUND, null, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    //         }

    //         return new ResponseEntity<>(new SearchUserResponse( Message.USER_FOUND_SUCCESS, dataUser, HttpStatus.OK), HttpStatus.OK);

    //     } catch (Exception e) {
    //         return new ResponseEntity<>(new SearchUserResponse( Message.ERROR_CREATING_USER_EXCEPTION + " " + e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    
    // }
    
 
    // UPDATE PUT


    


}
