package com.codewithahmed.ecommerce.user;

import com.codewithahmed.ecommerce.common.exception.UsersNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new UsersNotFoundException("No users found in the system.");
        } else {
            return users.stream()
                    .map(userMapper::toUserResponseDTO)
                    .collect(Collectors.toList());
        }
    }

    public UserResponseDTO getUserById(Long id) {
        Optional<User> user = userRepository.findById(id.intValue());

        if (user.isEmpty()) {
            throw new UsersNotFoundException("No user found in the system with this id -> " + id);
        } else {
            return userMapper.toUserResponseDTO(user.get());
        }
    }
}
