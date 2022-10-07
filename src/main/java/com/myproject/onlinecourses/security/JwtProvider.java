package com.myproject.onlinecourses.security;

import com.auth0.jwt.JWT;
import com.myproject.onlinecourses.utils.SecurityConstants;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtProvider {

    public String generateAccessToken(CustomUserDetails userDetails){

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setExpiration(new Date(new Date().getTime() + SecurityConstants.EXPIRATION_TIME))
                .claim("role", userDetails.getAuthorities())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, SecurityConstants.SECRET)
                .compact();
    }

    public String getUsername(String jwt){
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.SECRET.getBytes())
                .parseClaimsJws(jwt)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateJwt(String jwt){
        try {
            Jwts.parser()
                    .setSigningKey(SecurityConstants.SECRET.getBytes())
                    .parseClaimsJws(jwt)
                    .getBody();
            return true;
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty.");
        }
        return false;
    }
}
