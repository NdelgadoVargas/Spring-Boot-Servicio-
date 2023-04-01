package com.pruebatecnica.utils.token;
import javax.crypto.SecretKey;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class GenerateTokenJwt {
    
   private static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String generateToken(String email, String password){

        String token = Jwts.builder()
        .setSubject(email+"."+password)
        .signWith(key)
        .compact();

        return token;
    }
}
