package com.memo.gymapi.tutors.service;

import com.memo.gymapi.subjects.model.SubjectTutor;
import com.memo.gymapi.subjects.repositories.MateriaAsesorRepository;
import com.memo.gymapi.subjects.repositories.SubjectRepository;
import com.memo.gymapi.tutors.dto.TutorForListDto;
import com.memo.gymapi.tutors.dto.TutorsForListPaginatedDto;
import com.memo.gymapi.tutors.model.Tutor;
import com.memo.gymapi.tutors.repository.TutorRepository;
import com.memo.gymapi.user.User;
import com.memo.gymapi.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TutorService {
    private final TutorRepository tutorRepository;
    private final MateriaAsesorRepository materiaAsesorRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;

    public TutorService(TutorRepository tutorRepository, MateriaAsesorRepository materiaAsesorRepository, UserRepository userRepository, SubjectRepository subjectRepository) {
        this.tutorRepository = tutorRepository;
        this.materiaAsesorRepository = materiaAsesorRepository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
    }

    public TutorsForListPaginatedDto getAllTutors(Pageable pageable) {
        Page<Tutor> listTutors = tutorRepository.findAll(pageable);
        List<TutorForListDto> tutorsDtoList = new ArrayList<TutorForListDto>();
        for ( Tutor tutor: listTutors.getContent()) {
            TutorForListDto tutorForListDto = new TutorForListDto();
            User user = userRepository.getReferenceById(tutor.getUser().getId());
            tutorForListDto.setId(tutor.getId());
            tutorForListDto.setName(user.getFirstName() + " " + user.getLastName());
            tutorForListDto.setScore(tutor.getScore());
            List<SubjectTutor> listSubjects = materiaAsesorRepository.findAllByTutor(tutor);
            List<String> subjectNames = new ArrayList<String>();
            for(SubjectTutor subjectTutor: listSubjects) {
                subjectNames.add(subjectTutor.getSubjectClave().getNombre());
            }
            tutorForListDto.setSubjectNames(subjectNames);
            tutorsDtoList.add(tutorForListDto);
        }
        TutorsForListPaginatedDto tutorsForListPaginatedDto = new TutorsForListPaginatedDto();
        tutorsForListPaginatedDto.setTutorForListDtoList(tutorsDtoList);
        tutorsForListPaginatedDto.setTotalElements(listTutors.getTotalElements());

        return tutorsForListPaginatedDto;
    }
}
