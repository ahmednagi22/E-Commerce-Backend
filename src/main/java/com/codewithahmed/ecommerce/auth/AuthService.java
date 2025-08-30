package com.codewithahmed.ecommerce.auth;

import com.codewithahmed.ecommerce.address.Address;
import com.codewithahmed.ecommerce.address.AddressMapper;
import com.codewithahmed.ecommerce.common.exception.EmailAlreadyExistsException;
import com.codewithahmed.ecommerce.common.exception.ResourceNotFoundException;
import com.codewithahmed.ecommerce.user.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final AuthMapper authMapper;
    private final AddressMapper addressMapper;
    private final PasswordEncoder passwordEncoder;

    public UserAuthResponseDto registerUser(@Valid RegisterDto dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException("Email already registered: " + dto.getEmail());
        } else {

            var user = authMapper.toUser(dto);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Address address = addressMapper.toAddress(dto.getAddress());
            user.addAddress(address);
            userRepository.save(user);
            return authMapper.toDto(user);
        }
    }
}
