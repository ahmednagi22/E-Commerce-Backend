package com.codewithahmed.ecommerce.auth;

import com.codewithahmed.ecommerce.user.Role;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterDTO {

    @NotEmpty(message = "Name is required")
    private String name;
    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email")
    private String email;
    @NotEmpty(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    @Pattern(regexp = "^(\\+\\d{1,3})?[0-9]{10,11}$", message = "Invalid phone number")
    private String phone;
    private Role role;
}
