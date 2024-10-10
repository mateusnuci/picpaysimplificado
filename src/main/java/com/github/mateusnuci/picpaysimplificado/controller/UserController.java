package com.github.mateusnuci.picpaysimplificado.controller;


import com.github.mateusnuci.picpaysimplificado.domain.user.User;
import com.github.mateusnuci.picpaysimplificado.dto.UserDTO;
import com.github.mateusnuci.picpaysimplificado.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO user) {
        User newUser = userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> findAllUsers() {
        List<User> allUsers = userService.findAll();
        return ResponseEntity.ok(allUsers);
    }

}

