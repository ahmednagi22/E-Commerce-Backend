package com.codewithahmed.ecommerce.auth;

import com.codewithahmed.ecommerce.user.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<UserAuthResponseDto> registerUser(@Valid @RequestBody RegisterDto dto){
        return ResponseEntity.ok(authService.registerUser(dto));
        //return ResponseEntity.ok().build();
    }
}
