package com.memo.gymapi.subjects.service;

import com.memo.gymapi.subjects.dto.Day;
import com.memo.gymapi.subjects.dto.DayAvailability;
import com.memo.gymapi.subjects.dto.Period;
import com.memo.gymapi.subjects.dto.RegistrateScheduleRequest;
import com.memo.gymapi.subjects.model.Availability;
import com.memo.gymapi.subjects.model.SubjectTutor;
import com.memo.gymapi.subjects.repositories.AvailabilityRepository;
import com.memo.gymapi.subjects.repositories.MateriaAsesorRepository;
import com.memo.gymapi.subjects.repositories.SubjectRepository;
import com.memo.gymapi.tutors.model.Tutor;
import com.memo.gymapi.subjects.model.Subject;
import com.memo.gymapi.registration.repositories.AsesorRepository;
import com.memo.gymapi.user.User;
import com.memo.gymapi.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RegistrationSubjectsService {

    private final UserRepository userRepository;
    private final MateriaAsesorRepository materiaAsesorRepository;
    private final AsesorRepository asesorRepository;
    private final SubjectRepository subjectRepository;
    private final AvailabilityRepository availabilityRepository;

    public RegistrationSubjectsService(UserRepository userRepository, MateriaAsesorRepository materiaAsesorRepository, AsesorRepository asesorRepository, SubjectRepository subjectRepository, AvailabilityRepository availabilityRepository, AvailabilityRepository availabilityRepository1) {
        this.userRepository = userRepository;
        this.materiaAsesorRepository = materiaAsesorRepository;
        this.asesorRepository = asesorRepository;
        this.subjectRepository = subjectRepository;
        this.availabilityRepository = availabilityRepository1;
    }

    public void registerSubjects(List<String> subjectsNames) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userAuthentication = (User) authentication.getPrincipal();
        Integer id = userAuthentication.getId();
        User userDB = userRepository.findById(id).orElseThrow();

            Tutor tutor = asesorRepository.findByUser(userDB);
            ArrayList<SubjectTutor> subjects  = new ArrayList<>(subjectsNames.size());

            for(String subjectName : subjectsNames) {
//            Subject subject = new Subject();
//            subject.setNombre(subjectName);
//            subject.setId(clave);
//            subject.setAsesorId(tutor);
                Subject findSubject = subjectRepository.findByNombre(subjectName);
                if(findSubject == null){
                    throw new Exception("Subject not found");
                }
                SubjectTutor subjectTutor = new SubjectTutor();
                subjectTutor.setSubjectClave(findSubject);
                subjectTutor.setTutor(tutor);
                subjects.add(subjectTutor);
            }
            materiaAsesorRepository.saveAll(subjects);


    }

    public void registerAvailability(Map<Day, DayAvailability> availability) {
        List<Availability> availabilities = new ArrayList<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userAuthentication = (User) authentication.getPrincipal();
        User userDB = userRepository.findById(userAuthentication.getId()).orElseThrow();

        Tutor tutor = asesorRepository.getAsesorByUser(userDB);

        for (Map.Entry<Day, DayAvailability> entry : availability.entrySet() ) {
            Day day = entry.getKey();
            DayAvailability dayAvailability = entry.getValue();
            if(dayAvailability.isEnabled()){
                for (Period period : dayAvailability.getPeriods() ) {
                    Availability availabilityEntity = new Availability();
                    availabilityEntity.setDay(day);
                    availabilityEntity.setTutor(tutor);
                    availabilityEntity.setStartTime(LocalTime.parse(period.getStart()));
                    availabilityEntity.setEndTime(LocalTime.parse(period.getEnd()));
                    availabilities.add(availabilityEntity);
                }
            }
        }

        try{
            availabilityRepository.saveAll(availabilities);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Boolean register(RegistrateScheduleRequest request) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = null;
        if (authentication != null && authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            System.out.println(user.getEmail());
//            userRepository.updateFacultyAndOcupationByEmail(user.getEmail(), request.getFacultyUAM(), request.getOcupation());
            return true;
        }

        throw new Exception("Registration failed");
    }



}
