package com.fitness.userservices.controller;

import com.fitness.userservices.dto.RegisterRequest;
import com.fitness.userservices.response.UserResponse;
import com.fitness.userservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserProfile(@PathVariable String userId) {
        return ResponseEntity.ok(userService.getUserProfile(userId));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @GetMapping("/validate/{userId}")
    public ResponseEntity<Boolean> validateUserById(@PathVariable String userId) {
        return ResponseEntity.ok(userService.validateByUserId(userId));
    }
}
