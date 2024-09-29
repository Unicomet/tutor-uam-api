package com.memo.gymapi.tutorships.service;

import com.memo.gymapi.registration.model.Tutoree;
import com.memo.gymapi.registration.repositories.TutorRepository;
import com.memo.gymapi.registration.repositories.TutoreeRepository;
import com.memo.gymapi.subjects.dto.Day;
import com.memo.gymapi.subjects.model.Availability;
import com.memo.gymapi.subjects.repositories.AvailabilityRepository;
import com.memo.gymapi.tutors.model.Tutor;
import com.memo.gymapi.tutorships.dto.BookRequest;
import com.memo.gymapi.tutorships.dto.EvaluationTutorRequest;
import com.memo.gymapi.tutorships.dto.TutorshipsListResponse;
import com.memo.gymapi.tutorships.model.EvaluationTutor;
import com.memo.gymapi.tutorships.model.Tutorship;
import com.memo.gymapi.tutorships.repositories.EvaluationTutorRepository;
import com.memo.gymapi.tutorships.repositories.TutorShipRepository;
import com.memo.gymapi.user.User;
import com.memo.gymapi.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
public class TutorshipService {

    private final TutorShipRepository tutorShipRepository;
    private final AvailabilityRepository availabilityRepository;
    private final TutoreeRepository tutoreeRepository;
    private final TutorRepository tutorRepository;
    private final UserRepository userRepository;
    private final EvaluationTutorRepository evaluationTutorRepository;

    public TutorshipService(TutorShipRepository tutorShipRepository, AvailabilityRepository availabilityRepository, TutorRepository tutorRepository, TutoreeRepository tutoreeRepository, UserRepository userRepository, EvaluationTutorRepository evaluationTutorRepository) {
        this.tutorShipRepository = tutorShipRepository;
        this.availabilityRepository = availabilityRepository;
        this.tutoreeRepository = tutoreeRepository;
        this.tutorRepository = tutorRepository;
        this.userRepository = userRepository;
        this.evaluationTutorRepository = evaluationTutorRepository;
    }

    public boolean scheduleTutorship(BookRequest request) {
        LocalDate date = request.getDate();
        LocalTime time = request.getStartTime();
        LocalDateTime dateTime = LocalDateTime.of(date, time);

        //Verify if the datetime is after now
        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("The date and time must be after now");
        }
        //Verify that the datetime is in range of the availability of the tutor
        String dayNameSpanish = date.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
        String dayNameSpanishFormatted = dayNameSpanish.substring(0, 1).toUpperCase() + dayNameSpanish.substring(1).toLowerCase();
        Tutor tutor = tutorRepository.getReferenceById(request.getTutorId());
        List<Availability> availability = availabilityRepository.findSchedulesWithinTime(time, Day.valueOf(dayNameSpanishFormatted), tutor);

        if (availability.isEmpty()) {
            throw new RuntimeException("The tutor is not available at the requested time");
        }

        for (Availability availability1 : availability
        ) {
            System.out.println(availability1.toString());
        }
        //Verify that not another tutorship is already reserved for the same date and time

        if (tutorShipRepository.existsByDateTime(dateTime)) {
            throw new RuntimeException("The tutorship is already reserved");
        }
        //Schedule the tutorship
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        User userDb = userRepository.getReferenceById(user.getId());
        Tutoree tutoree = tutoreeRepository.findByUser(userDb);
        Tutorship tutorshipToSchedule = Tutorship.builder().dateTime(dateTime).tutoree(tutoree).tutor(tutor).build();
        tutorShipRepository.save(tutorshipToSchedule);

        return true;
    }

    public TutorshipsListResponse getTutorships() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Integer asesoradoId = tutoreeRepository.findIdByUserId(user.getId()).orElseThrow(
                () -> new RuntimeException("El usuario no es un asesorado")
        );

        List<Tutorship> tutorships = tutorShipRepository.findAllByTutoreeId(asesoradoId);
        if (tutorships.isEmpty()) {
            return new TutorshipsListResponse();
        }

        TutorshipsListResponse tutorshipResponse = new TutorshipsListResponse();
        tutorshipResponse.setTutorships(tutorships);

        return tutorshipResponse;
    }

    public void evaluateTutorForTutorship(EvaluationTutorRequest request) {
        Tutorship tutorship = tutorShipRepository.findById(request.getTutorshipId()).orElseThrow(
                () -> new RuntimeException("La tutoría no existe")
        );

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Tutoree tutoree = tutoreeRepository.findByUser(user);
        if (!Objects.equals(tutorship.getTutoree().getId(), tutoree.getId())) {
            throw new RuntimeException("La evaluación de esta tutoría no corresponde a este asesorado");
        }

        if (evaluationTutorRepository.existsEvaluationTutorByTutorshipId(tutorship.getId())) {
            throw new RuntimeException("La tutoría ya ha sido evaluada");
        }

        EvaluationTutor evaluationTutor = EvaluationTutor.builder()
                .calificacion(request.getRating())
                .descripcion(request.getDescription())
                .tutor(tutorship.getTutor())
                .tutorship(tutorship)
                .build();

        evaluationTutorRepository.save(evaluationTutor);
    }
}
