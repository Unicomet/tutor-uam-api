package com.memo.gymapi.tutors.service;

import com.memo.gymapi.tutors.dto.AvailabilityResponse;
import com.memo.gymapi.tutors.dto.TutorForListDTO;
import com.memo.gymapi.tutors.dto.TutorsForListPaginatedResponse;
import com.memo.gymapi.tutors.model.*;
import com.memo.gymapi.tutors.repositories.AvailabilityRepository;
import com.memo.gymapi.tutors.repositories.SubjectRepository;
import com.memo.gymapi.tutors.repositories.SubjectTutorRepository;
import com.memo.gymapi.tutors.repositories.TutorPageRepository;
import com.memo.gymapi.user.model.UserEntity;
import com.memo.gymapi.user.repositories.TutorRepository;
import com.memo.gymapi.user.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TutorService {
    private final TutorPageRepository tutorPageRepository;
    private final SubjectTutorRepository subjectTutorRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final TutorRepository tutorRepository;
    private final AvailabilityRepository availabilityRepository;
    private List<TutorForListDTO> tutorsDtoList;


    public TutorService(TutorPageRepository tutorPageRepository, SubjectTutorRepository subjectTutorRepository, UserRepository userRepository, SubjectRepository subjectRepository, TutorRepository tutorRepository, AvailabilityRepository availabilityRepository) {
        this.tutorPageRepository = tutorPageRepository;
        this.subjectTutorRepository = subjectTutorRepository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.tutorRepository = tutorRepository;
        this.availabilityRepository = availabilityRepository;
    }

    public TutorsForListPaginatedResponse getTutors(String name, String subject, Pageable pageable) {
        Page<TutorEntity> listTutors = tutorPageRepository.findAll(pageable);
        tutorsDtoList = new ArrayList<TutorForListDTO>();

        for (TutorEntity tutorEntity : listTutors.getContent()) {
            TutorForListDTO tutorForListDto = new TutorForListDTO();
            UserEntity userEntity = userRepository.getReferenceById(tutorEntity.getUserEntity().getId());
            tutorForListDto.setId(tutorEntity.getId());
            if (!Objects.equals(name, "")) {
                if (!userEntity.getFirstName().contains(name) && !userEntity.getLastName().contains(name)) {
                    continue;
                }
            }
            tutorForListDto.setName(userEntity.getFirstName() + " " + userEntity.getLastName());
            tutorForListDto.setScore(tutorEntity.getScore());
            List<SubjectTutor> listSubjects = subjectTutorRepository.findAllByTutorEntity(tutorEntity);
            List<String> subjectNames = new ArrayList<String>();

            Boolean hasSubject = false;
            for (SubjectTutor subjectTutor : listSubjects) {
                if (!Objects.equals(subject, "")) {
                    if (subjectTutor.getSubjectClave().getNombre().contains(subject)) {
                        hasSubject = true;
                    }
                }
                subjectNames.add(subjectTutor.getSubjectClave().getNombre());
            }
            if (!subject.isEmpty() && !hasSubject) {
                continue;
            }
            tutorForListDto.setSubjectNames(subjectNames);
            tutorsDtoList.add(tutorForListDto);
        }
        TutorsForListPaginatedResponse tutorsForListPaginatedResponse = new TutorsForListPaginatedResponse();
        tutorsForListPaginatedResponse.setTutorForListDtoList(tutorsDtoList);
        tutorsForListPaginatedResponse.setTotalElements((int) listTutors.getTotalElements());

        return tutorsForListPaginatedResponse;
    }

    public List<AvailabilityResponse> getTutorAvailability(Integer id) {
        tutorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("TutorEntity not found"));
        List<Availability> availabilities = availabilityRepository.findAllByTutorEntityId(id);

        return availabilities.stream().map(this::mapAvailabilityToDto).collect(Collectors.toList());
    }

    private AvailabilityResponse mapAvailabilityToDto(Availability availability) {
        AvailabilityResponse availabilityDto = new AvailabilityResponse();
        availabilityDto.setDay(availability.getDay());
        availabilityDto.setStartTime(availability.getStartTime());
        availabilityDto.setEndTime(availability.getEndTime());
        return availabilityDto;
    }

    public void registerSubjects(List<String> subjectsNames) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntityAuthentication = (UserEntity) authentication.getPrincipal();
        Integer id = userEntityAuthentication.getId();
        UserEntity userEntityDB = userRepository.findById(id).orElseThrow();

        TutorEntity tutorEntity = tutorRepository.findByUserEntity(userEntityDB);
        ArrayList<SubjectTutor> subjects = new ArrayList<>(subjectsNames.size());

        for (String subjectName : subjectsNames) {
//            Subject subject = new Subject();
//            subject.setNombre(subjectName);
//            subject.setId(clave);
//            subject.setAsesorId(tutorEntity);
            Subject findSubject = subjectRepository.findByNombre(subjectName);
            if (findSubject == null) {
                throw new Exception("Subject not found");
            }
            SubjectTutor subjectTutor = new SubjectTutor();
            subjectTutor.setSubjectClave(findSubject);
            subjectTutor.setTutorEntity(tutorEntity);
            subjects.add(subjectTutor);
        }
        subjectTutorRepository.saveAll(subjects);


    }

    public void registerAvailabilities(Map<Day, DayAvailability> availability) {
        List<Availability> availabilities = new ArrayList<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntityAuthentication = (UserEntity) authentication.getPrincipal();
        UserEntity userEntityDB = userRepository.findById(userEntityAuthentication.getId()).orElseThrow();

        TutorEntity tutorEntity = tutorRepository.getAsesorByUserEntity(userEntityDB);
        if (tutorEntity == null) {
            throw new RuntimeException("Perfil de Asesor no encontrado");
        }

        for (Map.Entry<Day, DayAvailability> entry : availability.entrySet()) {
            Day day = entry.getKey();
            DayAvailability dayAvailability = entry.getValue();
            if (dayAvailability.isEnabled()) {
                for (Period period : dayAvailability.getPeriods()) {
                    Availability availabilityEntity = new Availability();
                    availabilityEntity.setDay(day);
                    availabilityEntity.setTutorEntity(tutorEntity);
                    availabilityEntity.setStartTime(LocalTime.parse(period.getStart()));
                    availabilityEntity.setEndTime(LocalTime.parse(period.getEnd()));
                    availabilities.add(availabilityEntity);
                }
            }
        }

        try {
            availabilityRepository.saveAll(availabilities);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
