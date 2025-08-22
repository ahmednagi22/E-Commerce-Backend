package com.codewithahmed.ecommerce.user;

import com.codewithahmed.ecommerce.common.exception.UsersNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
            throw new UsersNotFoundException("No users found in the system.");
        } else {
            return users.stream()
                    .map(userMapper::toUserResponseDTO)
                    .collect(Collectors.toList());
        }
    }

    public UserResponseDto getUserById(Long id) {
        Optional<User> user = userRepository.findById(id.intValue());

        if (user.isPresent()) {
            return userMapper.toUserResponseDTO(user.get());
        } else {
            throw new UsersNotFoundException("User with id " + id + " not found");
        }
    }
}
