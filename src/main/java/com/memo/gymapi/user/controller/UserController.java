package com.memo.gymapi.user.controller;

import com.memo.gymapi.user.dto.RegistrationRequest;
import com.memo.gymapi.user.dto.UpdateProfileRequest;
import com.memo.gymapi.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PatchMapping(consumes = "application/json")
    public ResponseEntity<Void> login(@RequestBody UpdateProfileRequest request) {
        try {
            userService.updateProfile(request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping(value = "registro-comunidad", consumes = "application/json")
    public ResponseEntity<Void> register(@RequestBody RegistrationRequest request) {
        try {
            userService.register(request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}


