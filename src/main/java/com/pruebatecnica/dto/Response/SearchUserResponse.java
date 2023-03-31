package com.pruebatecnica.dto.Response;
import org.springframework.http.HttpStatus;
import com.pruebatecnica.servicioavlachile.entity.UserEntity;

public class SearchUserResponse {

    private String message;
    private UserEntity user;
    private HttpStatus httpStatus;

    public SearchUserResponse() {
    }

   public SearchUserResponse( String message, UserEntity user, HttpStatus httpStatus) {
        this.message = message;
        this.user = user;
        this.httpStatus = httpStatus;
    }
    
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
