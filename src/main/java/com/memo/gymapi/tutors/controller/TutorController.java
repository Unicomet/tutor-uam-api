package com.memo.gymapi.tutors.controller;

import com.memo.gymapi.tutors.dto.TutorsForListPaginatedDto;
import com.memo.gymapi.tutors.service.TutorService;
import jakarta.websocket.server.PathParam;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<TutorsForListPaginatedDto> geTutorByPageNameAndSubject(@RequestParam String name, @RequestParam String subject, Pageable pageable) {
        TutorsForListPaginatedDto response = tutorService.getTutors(name, subject, pageable);
        return ResponseEntity.ok().body(response);
    }

}
