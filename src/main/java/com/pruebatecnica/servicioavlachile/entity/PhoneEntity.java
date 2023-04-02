package com.pruebatecnica.servicioavlachile.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity()
@Table(name = "phones")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PhoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id;

    @Column()
    private String number;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

     public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNumber(String phone) {
        this.number = phone;
    }

    public void setUser(UserEntity id_users) {
        this.user = id_users;
    }
}
