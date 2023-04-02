package com.pruebatecnica.dto;
import java.util.ArrayList;
import java.util.List;
import com.pruebatecnica.servicioavlachile.entity.PhoneEntity;
import com.pruebatecnica.servicioavlachile.entity.UserEntity;

public class UserDTO {

    private String name;
    private String email;
    private String password;
    private List<PhoneDTO> phones;

    public UserEntity toEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(name);
        userEntity.setEmail(email);
        userEntity.setPassword(password);

        
        List<PhoneEntity> phoneEntities = new ArrayList<>();
        for (PhoneDTO phone : phones) {
            
            PhoneEntity phoneEntity = new PhoneEntity();
            phoneEntity.setNumber(phone.getNumber());
            phoneEntity.setUser(userEntity);
            phoneEntities.add(phoneEntity);
        }
        userEntity.setPhones(phoneEntities);

        return userEntity;
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
