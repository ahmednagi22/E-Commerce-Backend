package com.codewithahmed.ecommerce.auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "Email Is Required")
    @Email(message = "Invalid Email Format")
    private String email;
    @NotBlank(message = "Password Is Required")
    private String password;


}
