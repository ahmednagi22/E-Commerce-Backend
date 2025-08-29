package com.codewithahmed.ecommerce.auth;

import com.codewithahmed.ecommerce.address.Address;
import com.codewithahmed.ecommerce.address.AddressDto;
import com.codewithahmed.ecommerce.user.AccountStatus;
import com.codewithahmed.ecommerce.user.Role;

import lombok.Data;

import java.util.List;

@Data
public class UserAuthResponseDto {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private Role role;
    private AccountStatus accountStatus;
    private List<AddressDto> addresses;
}
