package com.pruebatecnica.dto;
import java.util.Date;
import java.util.List;

public class getUserDTO {

    private long id;
    private String name;
    private String email;
    private String password;
    private Date creationDate;
    private Date updateDate;
    private List<PhoneDTO> phones;

    public getUserDTO() {
    }

    public getUserDTO(long id, String name, String email, String password, Date creationDate, Date updateDate, List<PhoneDTO> phones) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.phones = phones;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public List<PhoneDTO> getPhones() {
        return phones;
    }
    public void setPhones(List<PhoneDTO> phones) {
        this.phones = phones;
    }

}
