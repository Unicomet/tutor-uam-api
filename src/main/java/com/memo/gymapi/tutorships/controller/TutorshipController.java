package com.memo.gymapi.tutorships.controller;

import com.memo.gymapi.tutorships.dto.BookRequest;
import com.memo.gymapi.tutorships.dto.EvaluationRequest;
import com.memo.gymapi.tutorships.dto.TutorshipsListResponse;
import com.memo.gymapi.tutorships.service.TutorshipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tutorships")
public class TutorshipController {

    private final TutorshipService tutorshipService;

    public TutorshipController(TutorshipService tutorshipService) {
        this.tutorshipService = tutorshipService;
    }

    @PostMapping(value = "schedule", consumes = "application/json")
    public ResponseEntity<String> bookTutorship(@RequestBody BookRequest request) {
        try {
            tutorshipService.scheduleTutorship(request);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "tutorships-for-tutoree")
    public ResponseEntity<Object> getTutorshipsForTutoree() {
        try {
            TutorshipsListResponse tutorshipsResponse = tutorshipService.getTutorshipsForTutoree();
            return ResponseEntity.ok().body(tutorshipsResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage()
            );
        }
    }

    @GetMapping(value = "tutorships-for-tutor")
    public ResponseEntity<Object> getTutorshipsForTutor() {
        try {
            TutorshipsListResponse tutorshipsResponse = tutorshipService.getTutorshipsForTutor();
            return ResponseEntity.ok().body(tutorshipsResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage()
            );
        }
    }

    @PostMapping(value = "evaluate-tutor", consumes = "application/json")
    public ResponseEntity<String> evaluateTutor(@RequestBody EvaluationRequest request) {
        try {
            tutorshipService.evaluateTutorForTutorship(request);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "evaluate-tutoree", consumes = "application/json")
    public ResponseEntity<String> evaluateTutoree(@RequestBody EvaluationRequest request) {
        try {
            tutorshipService.evaluateTutoreeForTutorship(request);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}


