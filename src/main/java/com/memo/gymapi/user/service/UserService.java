package com.memo.gymapi.user.service;

import com.memo.gymapi.tutors.model.TutorEntity;
import com.memo.gymapi.user.dto.RegistrationRequest;
import com.memo.gymapi.user.dto.UpdateProfileRequest;
import com.memo.gymapi.user.model.TutoreeEntity;
import com.memo.gymapi.user.model.UserEntity;
import com.memo.gymapi.user.repositories.TutorRepository;
import com.memo.gymapi.user.repositories.TutoreeRepository;
import com.memo.gymapi.user.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TutoreeRepository tutoreeRepository;
    private final TutorRepository tutorRepository;

    public UserService(UserRepository userRepository, TutoreeRepository tutoreeRepository, TutorRepository tutorRepository) {
        this.userRepository = userRepository;
        this.tutoreeRepository = tutoreeRepository;
        this.tutorRepository = tutorRepository;
    }

    public void updateProfile(UpdateProfileRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        UserEntity userEntityDb = userRepository.findById(userEntity.getId()).orElseThrow(
                () -> new RuntimeException("UserEntity not found")
        );

        if (request.getFirstName() != null) {
            userEntityDb.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            userEntityDb.setLastName(request.getLastName());
        }
        if (request.getOcupation() != null) {
            userEntityDb.setOcupation(request.getOcupation());
        }
        if (request.getFacultyUAM() != null) {
            userEntityDb.setFaculty(request.getFacultyUAM());
        }

        userRepository.save(userEntityDb);
    }

    public Boolean register(RegistrationRequest request) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = null;
        if (authentication != null && authentication.isAuthenticated()) {
            UserEntity userEntity = (UserEntity) authentication.getPrincipal();
            System.out.println(userEntity.getEmail());
            userRepository.updateFacultyAndOcupationByEmail(userEntity.getEmail(), request.getFacultyUAM(), request.getOcupation());
            saveOrUpdateRegistratrion(userEntity.getEmail(), request);
            return true;
        }
        throw new Exception("Registration failed");
    }

    public void saveOrUpdateRegistratrion(String email, RegistrationRequest request) {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("UserEntity not found")
        );

        //TODO: Refactor this, include exception handling
        if (Objects.equals(request.getRoleTutorship().toString(), "Asesorado")) {
            TutoreeEntity tutoreeEntity = TutoreeEntity.builder().userEntity(userEntity).build();
            tutoreeRepository.save(tutoreeEntity);
        } else if (Objects.equals(request.getRoleTutorship().toString(), "Asesor")) {
            TutorEntity tutorEntity = TutorEntity.builder().userEntity(userEntity).degree(request.getStudyField()).description(request.getDescription()).studyField(request.getStudyField()).tutorshipPlace(request.getTutorshipPlace()).build();
            tutorRepository.save(tutorEntity);
        }
    }

}
