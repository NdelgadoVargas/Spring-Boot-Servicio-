package com.pruebatecnica.dto.Response;
import org.springframework.http.HttpStatus;
public class CreateUserResponse {
    private long  id;
    private String created;
    private String modified;
    private String message;
    private HttpStatus httpStatus;
    private String token;

    public CreateUserResponse() {}

    public CreateUserResponse(long id, String created,String modified, String message, HttpStatus httpStatus, String token) {
        this.id = id;
        this.created = created;
        this.modified= modified;
        this.message = message;
        this.httpStatus = httpStatus;
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }
    
}
