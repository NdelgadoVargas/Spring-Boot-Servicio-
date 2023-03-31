package com.pruebatecnica.dto.Response;

import org.springframework.http.HttpStatus;

import com.pruebatecnica.servicioavlachile.entity.UserEntity;

public class ValidationTokenUserResponse {
    
    private String message;
    private UserEntity user;
    private HttpStatus status;
    
    public ValidationTokenUserResponse(String message, UserEntity user, HttpStatus status) {
        this.message = message;
        this.user = user;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public UserEntity getUser() {
        return user;
    }
    public void setUser(UserEntity user) {
        this.user = user;
    }
    public HttpStatus getStatus() {
        return status;
    }
    public void setStatus(HttpStatus status) {
        this.status = status;
    }

 // RETORNAR UN MENSAJE ERROR - RETORNAR DATA DE USER - HTTP STATUS

}
