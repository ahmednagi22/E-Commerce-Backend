package com.codewithahmed.ecommerce.auth;

import com.codewithahmed.ecommerce.config.JwtConfig;
import com.codewithahmed.ecommerce.user.UserResponseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class JwtService {

    private final JwtConfig jwtConfig;

    public Jwt generateAccessToken(UserResponseDto userDto) {
        return generateToken(userDto, jwtConfig.getAccessTokenExpiration());
    }

    public Jwt generateRefreshToken(UserResponseDto userDto) {
        return generateToken(userDto, jwtConfig.getRefreshTokenExpiration());
    }

    private Jwt generateToken(UserResponseDto userDto, long EXPIRATION_TIME) {
        var claims = Jwts.claims()
                .subject(userDto.getId().toString())
                .add("name", userDto.getName())
                .add("email", userDto.getEmail())
                .add("role", userDto.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * EXPIRATION_TIME))
                .build();
        return new Jwt(claims, jwtConfig.getSecretKey());
    }

    public Jwt parseToken(String token) {
        try {
            return new Jwt(getClaims(token), jwtConfig.getSecretKey());
        } catch (JwtException e) {
            return null;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
