package com.memo.gymapi.tutors.controller;

import com.memo.gymapi.tutors.dto.AvailabilityResponse;
import com.memo.gymapi.tutors.dto.RegistrateScheduleRequest;
import com.memo.gymapi.tutors.dto.TutorsForListPaginatedResponse;
import com.memo.gymapi.tutors.service.TutorService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tutors")

public class TutorController {
    private final TutorService tutorService;

    public TutorController(TutorService tutorService) {
        this.tutorService = tutorService;
    }

    @GetMapping("")
    public ResponseEntity<TutorsForListPaginatedResponse> geTutorByPageNameAndSubject(@RequestParam String name, @RequestParam String subject, Pageable pageable) {
        TutorsForListPaginatedResponse response = tutorService.getTutors(name, subject, pageable);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}/availabilities")
    public ResponseEntity<List<AvailabilityResponse>> getTutorAvailability(@PathVariable("id") Integer id) {
        List<AvailabilityResponse> response = tutorService.getTutorAvailability(id);
        return ResponseEntity.ok().body(response);
    }

    //TODO: Change the endpoint in Postman
    @PostMapping(value = "/{id}/availabilities", consumes = "application/json")
    public ResponseEntity<String> register(@PathVariable("id") Integer id, @RequestBody RegistrateScheduleRequest request) throws Exception {
        try {
            System.out.println("Registering subjects");
            tutorService.registerAvailabilities(request.getAvailability());
            tutorService.registerSubjects(request.getSubjects());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
