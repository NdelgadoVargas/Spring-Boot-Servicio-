package com.pruebatecnica.utils.Token;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import com.pruebatecnica.servicioavlachile.entity.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class TokenMananger {
    

    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private static final long EXPIRATION_TIME = 7200000;
    private Map<String, UserEntity> tokenMap = new HashMap<>(); 

    public String generateToken(UserEntity user) {
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        String token = Jwts.builder()
                .setSubject(user.getEmail() +"."+ user.getPassword())
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
        tokenMap.put(token, user);
        return token;
    }


    public UserEntity getUserFromToken(String token) {
        return tokenMap.get(token);
    }


}
