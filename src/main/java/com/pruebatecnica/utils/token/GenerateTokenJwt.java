package com.pruebatecnica.utils.token;
import java.util.Date;
import javax.crypto.SecretKey;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class GenerateTokenJwt {

    private static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String generateToken(String email, String password) {

        Date date = new Date();
        System.out.print(date);
        String token = Jwts.builder()
            .setSubject(email + "." + password + "." + date)
            .signWith(key)
            .compact();
        return token;
    }
}
