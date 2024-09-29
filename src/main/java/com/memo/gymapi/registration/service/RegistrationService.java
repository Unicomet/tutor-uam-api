package com.memo.gymapi.registration.service;

import com.memo.gymapi.registration.model.Tutoree;
import com.memo.gymapi.registration.repositories.TutoreeRepository;
import com.memo.gymapi.tutors.model.Tutor;
import com.memo.gymapi.registration.repositories.TutorRepository;
import com.memo.gymapi.registration.requests.RegistrationRequest;
import com.memo.gymapi.user.User;
import com.memo.gymapi.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final TutoreeRepository tutoreeRepository;
    private final TutorRepository tutorRepository;

    public RegistrationService(UserRepository userRepository, TutoreeRepository tutoreeRepository, TutorRepository tutorRepository) {
        this.userRepository = userRepository;
        this.tutorRepository = tutorRepository;
        this.tutoreeRepository = tutoreeRepository;
    }

    public Boolean register(RegistrationRequest request) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = null;
        if (authentication != null && authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            System.out.println(user.getEmail());
            userRepository.updateFacultyAndOcupationByEmail(user.getEmail(), request.getFacultyUAM(), request.getOcupation());
            saveOrUpdateRegistratrion(user.getEmail(), request);
            return true;
        }
        throw new Exception("Registration failed");
    }

    public void saveOrUpdateRegistratrion(String email, RegistrationRequest request ) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        //TODO: Refactor this, include exception handling
        if(Objects.equals(request.getRoleTutorship().toString(), "Asesorado")){
            Tutoree tutoree = Tutoree.builder().user(user).build();
            tutoreeRepository.save(tutoree);
        }else if(Objects.equals(request.getRoleTutorship().toString(), "Asesor")){
            Tutor tutor = Tutor.builder().user(user).degree(request.getStudyField()).description(request.getDescription()).studyField(request.getStudyField()).build();
            tutorRepository.save(tutor);
        }
    }
}