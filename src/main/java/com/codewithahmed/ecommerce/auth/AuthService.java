package com.codewithahmed.ecommerce.auth;

import com.codewithahmed.ecommerce.address.Address;
import com.codewithahmed.ecommerce.address.AddressMapper;
import com.codewithahmed.ecommerce.common.exception.EmailAlreadyExistsException;
import com.codewithahmed.ecommerce.user.User;
import com.codewithahmed.ecommerce.user.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final AuthMapper authMapper;
    private final AddressMapper addressMapper;

    public UserAuthResponseDto registerUser(@Valid RegisterDto dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException("Email already registered: " + dto.getEmail());
        } else {

            var user = authMapper.toUser(dto);
            Address address = addressMapper.toAddress(dto.getAddress());
            user.addAddress(address);
            userRepository.save(user);
            return authMapper.toDto(user);
        }
    }
}
