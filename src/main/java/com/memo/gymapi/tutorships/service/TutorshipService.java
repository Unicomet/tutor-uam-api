package com.memo.gymapi.tutorships.service;

import com.memo.gymapi.tutors.model.Availability;
import com.memo.gymapi.tutors.model.Day;
import com.memo.gymapi.tutors.model.TutorEntity;
import com.memo.gymapi.tutors.repositories.AvailabilityRepository;
import com.memo.gymapi.tutorships.dto.BookRequest;
import com.memo.gymapi.tutorships.dto.EvaluationRequest;
import com.memo.gymapi.tutorships.dto.TutorshipsListResponse;
import com.memo.gymapi.tutorships.model.EvaluationTutorEntity;
import com.memo.gymapi.tutorships.model.EvaluationTutoreeEntity;
import com.memo.gymapi.tutorships.model.TutorshipEntity;
import com.memo.gymapi.tutorships.repositories.EvaluationTutorRepository;
import com.memo.gymapi.tutorships.repositories.EvaluationTutoreeRepository;
import com.memo.gymapi.tutorships.repositories.TutorShipRepository;
import com.memo.gymapi.user.model.TutoreeEntity;
import com.memo.gymapi.user.model.UserEntity;
import com.memo.gymapi.user.repositories.TutorRepository;
import com.memo.gymapi.user.repositories.TutoreeRepository;
import com.memo.gymapi.user.repositories.UserRepository;
import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
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
    private final EvaluationTutoreeRepository evaluationTutoreeRepository;

    public TutorshipService(TutorShipRepository tutorShipRepository, AvailabilityRepository availabilityRepository, TutorRepository tutorRepository, TutoreeRepository tutoreeRepository, UserRepository userRepository, EvaluationTutorRepository evaluationTutorRepository, EvaluationTutoreeRepository evaluationTutoreeRepository) {
        this.tutorShipRepository = tutorShipRepository;
        this.availabilityRepository = availabilityRepository;
        this.tutoreeRepository = tutoreeRepository;
        this.tutorRepository = tutorRepository;
        this.userRepository = userRepository;
        this.evaluationTutorRepository = evaluationTutorRepository;
        this.evaluationTutoreeRepository = evaluationTutoreeRepository;
    }

    public boolean scheduleTutorship(BookRequest request) {
        LocalDate date = request.getDate();
        LocalTime time = request.getStartTime();
        LocalDateTime dateTime = LocalDateTime.of(date, time);

        //Verify if the datetime is after now
        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("The date and time must be after now");
        }
        //Verify that the datetime is in range of the availability of the tutorEntity
        String dayNameSpanish = date.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
        String dayNameSpanishFormatted = dayNameSpanish.substring(0, 1).toUpperCase() + dayNameSpanish.substring(1).toLowerCase();
        TutorEntity tutorEntity = tutorRepository.getReferenceById(request.getTutorId());
        List<Availability> availability = availabilityRepository.findSchedulesWithinTime(time, Day.valueOf(dayNameSpanishFormatted), tutorEntity);

        if (availability.isEmpty()) {
            throw new RuntimeException("The tutorEntity is not available at the requested time");
        }

        for (Availability availability1 : availability
        ) {
            System.out.println(availability1.toString());
        }
        //Verify that not another tutorshipEntity is already reserved for the same date and time
        if (tutorShipRepository.existsByDateTime(dateTime)) {
            throw new RuntimeException("The tutorshipEntity is already reserved");
        }

        //Schedule the tutorshipEntity
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        UserEntity userEntityDb = userRepository.getReferenceById(userEntity.getId());
        TutoreeEntity tutoree = tutoreeRepository.findByUserEntity(userEntityDb);
        TutorshipEntity tutorshipEntityToSchedule = TutorshipEntity.builder().dateTime(dateTime).tutoree(tutoree).tutorEntity(tutorEntity).isTutorshipInPerson(request.getIsTutorshipInPerson()).build();
        tutorShipRepository.save(tutorshipEntityToSchedule);

        //Enviar correos al asesor y asesorado
        String emailTutoree = userEntityDb.getEmail();
        String emailTutor = tutorEntity.getUserEntity().getEmail();

        Resend resend = new Resend("re_8jA8GTEt_NLeXHJCd86wFZncDwrP36yBg");

        String tutorshipType = "";
        if (request.getIsTutorshipInPerson()) {
            tutorshipType = "presencial";
        } else {
            tutorshipType = "remota";
        }

        CreateEmailOptions paramsForTutor = CreateEmailOptions.builder()
                .from("onboarding@resend.dev")
                .to("martinezguillermo559@gmail.com")
                .subject("Asesoría agendada")
                .html("<p> La asesoría quedo agendad de forma " + tutorshipType + " para la fecha " + date + " a las " + time + "</p>")
                .build();

//        CreateEmailOptions paramsForTutoree = CreateEmailOptions.builder()
//                .from("TutorUAM <tutoruam.com>")
//                .to(emailTutor)
//                .subject("Asesoría agendada")
//                .html("<p> La asesoría quedo agendad de forma " + tutorshipType + " para la fecha " + date + " a las " + time + "</p>")
//                .build();

        System.out.println("<p> La asesoría quedo agendada de forma " + tutorshipType + " para la fecha " + date + " a las " + time + "</p>");

        try {
            CreateEmailResponse dataTutor = resend.emails().send(paramsForTutor);
//            CreateEmailResponse dataTutoree = resend.emails().send(paramsForTutoree);
            System.out.println(dataTutor.getId());
//            System.out.println(dataTutoree.getId());
        } catch (ResendException e) {
            e.printStackTrace();
        }

        return true;
    }

    public TutorshipsListResponse getTutorshipsForTutoree() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Integer asesoradoId = tutoreeRepository.findIdByUserId(userEntity.getId()).orElseThrow(
                () -> new RuntimeException("El usuario no es un asesorado")
        );

        List<TutorshipEntity> tutorshipEntities = tutorShipRepository.findAllByTutoreeId(asesoradoId);
        if (tutorshipEntities.isEmpty()) {
            return new TutorshipsListResponse();
        }

        TutorshipsListResponse tutorshipResponse = new TutorshipsListResponse();
        tutorshipResponse.setTutorshipEntities(tutorshipEntities);

        return tutorshipResponse;
    }

    public TutorshipsListResponse getTutorshipsForTutor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        Integer tutorId = tutorRepository.findIdByUserId(userEntity.getId()).orElseThrow(
                () -> new RuntimeException("El usuario no es un asesor")
        );

        List<TutorshipEntity> tutorshipEntities = tutorShipRepository.findAllByTutorEntityId(tutorId);
        if (tutorshipEntities.isEmpty()) {
            return new TutorshipsListResponse();
        }

        TutorshipsListResponse tutorshipResponse = new TutorshipsListResponse();
        tutorshipResponse.setTutorshipEntities(tutorshipEntities);

        return tutorshipResponse;
    }

    public void evaluateTutorForTutorship(EvaluationRequest request) {


        TutorshipEntity tutorshipEntity = tutorShipRepository.findById(request.getTutorshipId()).orElseThrow(
                () -> new RuntimeException("La tutoría no existe")
        );

        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TutoreeEntity tutoree = tutoreeRepository.findByUserEntity(userEntity);

        if (tutoree == null) {
            throw new RuntimeException("El usuario no es un asesorado, no puede calificar a un asesor");
        }

        if (!Objects.equals(tutorshipEntity.getTutoree().getId(), tutoree.getId())) {
            throw new RuntimeException("La evaluación de esta tutoría no corresponde a este asesorado");
        }

        if (evaluationTutorRepository.existsEvaluationTutorByTutorshipEntityId(tutorshipEntity.getId())) {
            throw new RuntimeException("La tutoría ya ha sido evaluada");
        }

        EvaluationTutorEntity evaluationTutor = EvaluationTutorEntity.builder()
                .calificacion(request.getRating())
                .descripcion(request.getDescription())
                .tutorEntity(tutorshipEntity.getTutorEntity())
                .tutorshipEntity(tutorshipEntity)
                .build();

        evaluationTutorRepository.save(evaluationTutor);
    }

    public void evaluateTutoreeForTutorship(EvaluationRequest request) {
        TutorshipEntity tutorshipEntity = tutorShipRepository.findById(request.getTutorshipId()).orElseThrow(
                () -> new RuntimeException("La tutoría no existe")
        );

        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TutorEntity tutorEntity = tutorRepository.findByUserEntity(userEntity);
        if (tutorEntity == null) {
            throw new RuntimeException("El usuario no es un asesor, no puede calificar a un asesorado");
        }

        if (!Objects.equals(tutorshipEntity.getTutorEntity().getId(), tutorEntity.getId())) {
            throw new RuntimeException("La evaluación del alumno en esta asesoría,  no corresponde a este asesor");
        }

        if (evaluationTutoreeRepository.existsEvaluationTutoreeEntityByTutorshipEntityId(tutorshipEntity.getId())) {
            throw new RuntimeException("La tutoría ya ha sido evaluada");
        }

        EvaluationTutoreeEntity evaluationTutoreeEntity = EvaluationTutoreeEntity.builder()
                .calificacion(request.getRating())
                .descripcion(request.getDescription())
                .tutoree(tutorshipEntity.getTutoree())
                .tutorshipEntity(tutorshipEntity)
                .build();

        evaluationTutoreeRepository.save(evaluationTutoreeEntity);
    }


}
