package com.codewithahmed.ecommerce.auth;

import com.codewithahmed.ecommerce.user.UserResponseDto;
import com.codewithahmed.ecommerce.user.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${secret}")
    private String secret;
    private final UserService userService;
    //    public String generateJwtToken(String email) {
//        final long EXPIRATION_TIME = 86400; //1day
//        return Jwts.builder()
//                .subject(email)
//                .issuedAt(new Date())
//                .expiration(new Date(System.currentTimeMillis() + 1000 * EXPIRATION_TIME))
//                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
//                .compact();
//    }
public String generateJwtToken(UserResponseDto userDto) {
    final long EXPIRATION_TIME = 86400; //  1day
    return Jwts.builder()
            .subject(userDto.getId().toString())
            .claim("name", userDto.getName())
            .claim("email", userDto.getEmail())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + 1000 * EXPIRATION_TIME))
            .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
            .compact();
}

    public boolean validateJwtToken(String token) {
        try {
            var claims = getClaims(token);

            return claims.getExpiration().after(new Date());

        } catch (JwtException e) {
            return false;
        }

    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Long getUserIdFromToken(String token) {
        return Long.valueOf(getClaims(token).getSubject());
    }

}
