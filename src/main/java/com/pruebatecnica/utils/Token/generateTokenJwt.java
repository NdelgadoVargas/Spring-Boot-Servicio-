package com.pruebatecnica.utils.Token;
import javax.crypto.SecretKey;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class generateTokenJwt {
    
    public String generateToken(String email, String password){

        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String token = Jwts.builder()
        .setSubject(email+"."+password)
        .signWith(key)
        .compact();

        return token;
    }
}
