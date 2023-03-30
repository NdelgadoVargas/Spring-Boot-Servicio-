package com.pruebatecnica.servicioavlachile.entity;
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
public class PhoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id;

    @Column()
    private String number;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    //  Getters
     public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public UserEntity getUser() {
        return user;
    }

    // Setters
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
