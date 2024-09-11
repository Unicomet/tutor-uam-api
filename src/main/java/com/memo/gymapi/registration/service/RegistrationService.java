package com.memo.gymapi.registration.service;

import com.memo.gymapi.tutors.model.Tutor;
import com.memo.gymapi.registration.model.Asesorado;
import com.memo.gymapi.registration.repositories.AsesorRepository;
import com.memo.gymapi.registration.repositories.AsesoradoRepository;
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
    private final AsesoradoRepository asesoradoRepository;
    private final AsesorRepository asesorRepository;

    public RegistrationService(UserRepository userRepository, AsesoradoRepository asesoradoRepository, AsesorRepository asesorRepository) {
        this.userRepository = userRepository;
        this.asesoradoRepository = asesoradoRepository;
        this.asesorRepository = asesorRepository;
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
            Asesorado asesorado = Asesorado.builder().user(user).build();
            asesoradoRepository.save(asesorado);
        }else if(Objects.equals(request.getRoleTutorship().toString(), "Asesor")){
            Tutor tutor = Tutor.builder().user(user).degree(request.getStudyField()).description(request.getDescription()).studyField(request.getStudyField()).build();
            asesorRepository.save(tutor);
        }
    }
}