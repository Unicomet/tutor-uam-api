package com.memo.gymapi.auth;


import com.memo.gymapi.jwt.JwtService;
import com.memo.gymapi.user.model.UserEntity;
import com.memo.gymapi.user.repositories.TutorRepository;
import com.memo.gymapi.user.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TutorRepository tutorRepository;

    /*
    Authenticates the userEntity and returns a token (JWT) if the userEntity exists
     */
    public AuthResponse login(LoginRequest request) {
        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(request.getEmail(), request.getPassword());

        try {
            authenticationManager.authenticate(
                    authenticationRequest);
        } catch (Exception e) {
            throw new EntityNotFoundException("Invalid credentials");
        }

        UserEntity user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new EntityNotFoundException("User not found")
        );
        // Generate token and return response
        String token = jwtService.getToken(user);
        return AuthResponse.builder().token(token).isTutor(tutorRepository.existsByUserEntityId(user.getId())).build();
    }

    public AuthResponse register(RegisterRequest request) {
        UserEntity userEntity = UserEntity.builder()
                .firstName(request.firstName)
                .lastName(request.lastName)
                .email(request.email)
                .password(passwordEncoder.encode(request.password))
                .faculty(request.faculty)
                .ocupation(request.ocupation)
                .build();

        userRepository.save(userEntity);

        return AuthResponse.builder()
                .token(jwtService.getToken(userEntity))
                .build();
    }
}
