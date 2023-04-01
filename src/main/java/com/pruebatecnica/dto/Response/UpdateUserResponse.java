package com.pruebatecnica.dto.Response;
import org.springframework.http.HttpStatus;

public class UpdateUserResponse {
    
    private long id;
    private String message;
    private String created;
    private String modified;
    private String updateToken;
    private HttpStatus httpStatus;
    
    public UpdateUserResponse() {}
    
    public UpdateUserResponse(long id, String message, String created, String modified,String updateToken, HttpStatus httpStatus) {
        this.id = id;
        this.message = message;
        this.created = created;
        this.modified = modified;
        this.updateToken = updateToken;
        this.httpStatus = httpStatus;
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

    public String getUpdateToken() {
        return updateToken;
    }

    public void setUpdateToken(String updateToken) {
        this.updateToken = updateToken;
    }
    
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

  




}
