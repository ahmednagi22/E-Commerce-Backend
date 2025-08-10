package com.codewithahmed.ecommerce.user;

import com.codewithahmed.ecommerce.common.exception.UsersNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        if(users.isEmpty()){
            throw new UsersNotFoundException("No users found in the system.");
        }
        else{
            return users.stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        }
    }

    private UserResponseDTO mapToDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getPhone(),
                user.isEmail_verified(),
                user.getStatus(),
                user.is_active()
        );
    }

    public UserResponseDTO getUserById(Long id) {
        Optional<User> user = userRepository.findById(id.intValue());

        if(user.isEmpty()){
            throw new UsersNotFoundException("No user found in the system with this id -> "+id);
        }
        else{
            return mapToDTO(user.get());
        }
    }
}
