package com.pruebatecnica.servicioavlachile.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pruebatecnica.servicioavlachile.entity.UserTokenEntity;

@Repository
public interface UserTokenRepository extends JpaRepository<UserTokenEntity, Long> {
    
    UserTokenEntity findByToken(String token);
}