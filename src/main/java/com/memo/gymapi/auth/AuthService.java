package com.memo.gymapi.auth;


import com.memo.gymapi.auth.exceptions.InvalidCredentialsException;
import com.memo.gymapi.jwt.JwtService;
import com.memo.gymapi.registration.repositories.TutorRepository;
import com.memo.gymapi.user.model.User;
import com.memo.gymapi.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
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
    Authenticates the user and returns a token (JWT) if the user exists
     */
    public AuthResponse login(LoginRequest request) {
        try {
            Authentication authenticationRequest =
                    UsernamePasswordAuthenticationToken.unauthenticated(request.getEmail(), request.getPassword());

            authenticationManager.authenticate(
                    authenticationRequest
            );

            UserDetails userDetails = userRepository.findByEmail(request.getEmail()).orElseThrow();
            // Generate token and return response
            String token = jwtService.getToken(userDetails);
            //Todo: Change the next line, it's not efficient
            User user = userRepository.findByEmail(request.getEmail()).orElseThrow();

            return AuthResponse.builder().token(token).isTutor(tutorRepository.existsByUserId(user.getId())).build();
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
    }

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .firstName(request.firstName)
                .lastName(request.lastName)
                .email(request.email)
                .password(passwordEncoder.encode(request.password))
                .faculty(request.faculty)
                .ocupation(request.ocupation)
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}
