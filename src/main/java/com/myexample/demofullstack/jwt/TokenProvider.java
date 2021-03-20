package com.myexample.demofullstack.jwt;

import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

import static com.myexample.demofullstack.jwt.JwtConst.SECRET;

@Service
public class TokenProvider {
    public TokenProvider() {
    }

    public String generateToken(Authentication authResult){
        String jwtToken = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("Authorities", authResult.getAuthorities())
                // .setExpiration(Date.valueOf(LocalDate.now().plusYears(1)))
                .setIssuedAt(new java.util.Date())
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        return jwtToken;
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        }catch (SignatureException e){
            System.out.println("Invalid JWT Signature");
        } catch (MalformedJwtException e){
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException e){
            System.out.println("Token expired");
        } catch (UnsupportedJwtException e){
            System.out.println("Unsupported token");
        } catch (IllegalArgumentException e){
            System.out.println("Token claims empty");
        }
        return false;
    }

    public String getUsernameFromJwt(String token){
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }


}
