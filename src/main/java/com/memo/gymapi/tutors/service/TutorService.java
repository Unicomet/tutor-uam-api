package com.memo.gymapi.tutors.service;

import com.memo.gymapi.subjects.model.SubjectTutor;
import com.memo.gymapi.subjects.repositories.SubjectTutorRepository;
import com.memo.gymapi.subjects.repositories.SubjectRepository;
import com.memo.gymapi.tutors.dto.TutorForListDto;
import com.memo.gymapi.tutors.dto.TutorsForListPaginatedDto;
import com.memo.gymapi.tutors.model.Tutor;
import com.memo.gymapi.tutors.repositories.TutorPageRepository;
import com.memo.gymapi.user.User;
import com.memo.gymapi.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TutorService {
    private final TutorPageRepository tutorPageRepository;
    private final SubjectTutorRepository subjectTutorRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private List<TutorForListDto> tutorsDtoList;


    public TutorService(TutorPageRepository tutorPageRepository, SubjectTutorRepository subjectTutorRepository, UserRepository userRepository, SubjectRepository subjectRepository) {
        this.tutorPageRepository = tutorPageRepository;
        this.subjectTutorRepository = subjectTutorRepository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
    }

    public TutorsForListPaginatedDto getTutors(String name, String subject,Pageable pageable) {
        Page<Tutor> listTutors = tutorPageRepository.findAll(pageable);
        tutorsDtoList = new ArrayList<TutorForListDto>();

        for ( Tutor tutor: listTutors.getContent()) {
            TutorForListDto tutorForListDto = new TutorForListDto();
            User user = userRepository.getReferenceById(tutor.getUser().getId());
            tutorForListDto.setId(tutor.getId());
            if(!Objects.equals(name, "")){
                if(!user.getFirstName().contains(name) && !user.getLastName().contains(name)){
                    continue;
                }
            }
            tutorForListDto.setName(user.getFirstName() + " " + user.getLastName());
            tutorForListDto.setScore(tutor.getScore());
            List<SubjectTutor> listSubjects = subjectTutorRepository.findAllByTutor(tutor);
            List<String> subjectNames = new ArrayList<String>();

            Boolean hasSubject =false;
            for(SubjectTutor subjectTutor: listSubjects) {
                if(!Objects.equals(subject, "")){
                    if(subjectTutor.getSubjectClave().getNombre().contains(subject)){
                        hasSubject = true;
                    }
                }
                subjectNames.add(subjectTutor.getSubjectClave().getNombre());
            }
            if(!subject.isEmpty() && hasSubject == false){
                continue;
            }
            tutorForListDto.setSubjectNames(subjectNames);
            tutorsDtoList.add(tutorForListDto);
        }
        TutorsForListPaginatedDto tutorsForListPaginatedDto = new TutorsForListPaginatedDto();
        tutorsForListPaginatedDto.setTutorForListDtoList(tutorsDtoList);
        tutorsForListPaginatedDto.setTotalElements((int) listTutors.getTotalElements());

        return tutorsForListPaginatedDto;
    }
}
