package com.codewithahmed.ecommerce.auth;

import com.codewithahmed.ecommerce.config.JwtConfig;
import com.codewithahmed.ecommerce.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtConfig jwtConfig;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserAuthResponseDto> registerUser(@Valid @RequestBody RegisterDto dto) {
        System.out.println("registering user");
        return ResponseEntity.ok(authService.registerUser(dto));
    }


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request,
                                             HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userService.getUserByEmail(request.getEmail());

        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        var cookie = new Cookie("refreshToken", refreshToken.toString());
        cookie.setHttpOnly(true);
        cookie.setPath("/auth/refresh");
        cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());//7 days
        cookie.setSecure(true);
        response.addCookie(cookie);

        return ResponseEntity.ok(new JwtResponse(accessToken.toString()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshToken(@CookieValue("refreshToken") String refreshToken) {
        var jwt = jwtService.parseToken(refreshToken);
        if(jwt == null||jwt.isExpired()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        var user = userService.getUserById(jwt.getUserId());
        var accessToken = jwtService.generateAccessToken(user);
        return ResponseEntity.ok(new JwtResponse(accessToken.toString()));
    }


//    @PostMapping("/validate")
//    public boolean validateToken(@RequestHeader("Authorization") String authHeader) {
//        var token = authHeader.replace("Bearer ", "");
//        return jwtService.validateJwtToken(token);
//    }
}
