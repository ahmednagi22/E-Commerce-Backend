package com.codewithahmed.ecommerce.user;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    //get user by id
    //add new user
    //update user
    //delete user
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>>getAllUsers(){
            return new ResponseEntity<>(
                    userService.getAllUsers(),
                    HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto>getUser(@PathVariable Long id){
        return  ResponseEntity.ok(userService.getUserById(id));
    }

}
