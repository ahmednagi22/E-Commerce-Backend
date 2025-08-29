package com.codewithahmed.ecommerce.auth;

import com.codewithahmed.ecommerce.user.AccountStatus;
import com.codewithahmed.ecommerce.user.Role;

import lombok.Data;

@Data
public class UserAuthResponseDTO {

    private String name;
    private String email;
    private String password;
    private String phone;
    private Role role;
    private AccountStatus status;
    private boolean is_active;
    private boolean email_verified;
}
