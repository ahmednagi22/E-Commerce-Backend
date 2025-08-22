package com.codewithahmed.ecommerce.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private String phone;
    private boolean email_verified;
    private AccountStatus status;
    private boolean is_active;
}
