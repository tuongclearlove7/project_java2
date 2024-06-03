package com.example.testlogin.project_java2.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JWTGeneratorToken {


    public String generateToken(Authentication authentication){

        String email = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + Security.JWT_EXPIRATION);

        return Jwts.builder().setSubject(email).setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, Security.JWT_SECRET)
                .compact();
    }


    public String generateTokenExpiration(String username) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + 1000 * 60 * 60);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, Security.JWT_SECRET)
                .compact();
    }


    public String getEmailFromJWT(String token){

        Claims claims = Jwts.parser().setSigningKey(Security.JWT_SECRET)
                .parseClaimsJws(token).getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {

            Jwts.parser().setSigningKey(Security.JWT_SECRET).parseClaimsJws(token);

            return true;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect",ex.fillInStackTrace());
        }
    }

}
