package com.codewithahmed.ecommerce.user;

import com.codewithahmed.ecommerce.common.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.hibernate.mapping.Collection;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users found in the system.");
        } else {
            return users.stream()
                    .map(userMapper::toUserResponseDTO)
                    .collect(Collectors.toList());
        }
    }

    public UserResponseDto getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            return userMapper.toUserResponseDTO(user.get());
        } else {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserResponseDto getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return userMapper.toUserResponseDTO(user.get());
        }else {
            throw new ResourceNotFoundException("User with email " + email + " not found");
        }

    }
}
