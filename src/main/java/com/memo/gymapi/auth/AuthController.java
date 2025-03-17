package com.memo.gymapi.auth;

import com.memo.gymapi.auth.exceptions.InvalidCredentialsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "login", consumes = "application/json")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) throws EntityNotFoundException, InvalidCredentialsException, AuthenticationException {
        return authService.login(request);
    }

    @PostMapping(value = "register", consumes = "application/json")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

}
