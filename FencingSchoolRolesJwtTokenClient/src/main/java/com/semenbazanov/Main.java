package com.semenbazanov;

import com.semenbazanov.retrofit.AuthenticationRepository;
import io.jsonwebtoken.*;

import java.io.IOException;
import java.util.Base64;

public class Main {
    public static void main(String[] args) {
        try {
            AuthenticationRepository authenticationRepository = new AuthenticationRepository(null);
            String token = authenticationRepository.authentication("Admin", "Admin");
            System.out.println(token);

            String secret = "fencingschooljwt";
            secret = Base64.getEncoder().encodeToString(secret.getBytes());
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

            String username = claims.getBody().getSubject();
            System.out.println(username);
            String role = (String) claims.getBody().get("role");
            System.out.println(role);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}