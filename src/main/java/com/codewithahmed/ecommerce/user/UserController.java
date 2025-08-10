package com.codewithahmed.ecommerce.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    //get user by id
    //add new user
    //update user
    //delete user
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<List<UserResponseDTO>>getAllUsers(){
            return new ResponseEntity<>(
                    userService.getAllUsers(),
                    HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO>getUserById(@PathVariable Long id){
        return new ResponseEntity<>(
                userService.getUserById(id),
                HttpStatus.OK);
    }

}
