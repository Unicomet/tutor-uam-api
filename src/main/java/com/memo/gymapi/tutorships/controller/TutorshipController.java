package com.memo.gymapi.tutorships.controller;

import com.memo.gymapi.tutorships.dto.BookRequest;
import com.memo.gymapi.tutorships.dto.EvaluationTutorRequest;
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

    @GetMapping()
    public ResponseEntity<TutorshipsListResponse> getTutorships() {
        try {
            TutorshipsListResponse tutorshipsResponse = tutorshipService.getTutorships();
            return ResponseEntity.ok().body(tutorshipsResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null
            );
        }
    }

    @PostMapping(value = "evaluate", consumes = "application/json")
    public ResponseEntity<String> evaluateTutorship(@RequestBody EvaluationTutorRequest request) {
        try {
            tutorshipService.evaluateTutorForTutorship(request);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}


