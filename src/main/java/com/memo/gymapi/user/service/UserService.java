package com.memo.gymapi.user.service;

import com.memo.gymapi.user.dto.UpdateProfileRequest;
import com.memo.gymapi.user.model.User;
import com.memo.gymapi.user.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void updateProfile(UpdateProfileRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        User userDb = userRepository.findById(user.getId()).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        if (request.getFirstName() != null) {
            userDb.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            userDb.setLastName(request.getLastName());
        }
        if (request.getOcupation() != null) {
            userDb.setOcupation(request.getOcupation());
        }
        if (request.getFacultyUAM() != null) {
            userDb.setFaculty(request.getFacultyUAM());
        }

        userRepository.save(userDb);
    }
}
