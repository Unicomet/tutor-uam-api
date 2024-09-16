package com.memo.gymapi.tutors.controller;

import com.memo.gymapi.tutors.dto.TutorsForListPaginatedDto;
import com.memo.gymapi.tutors.service.TutorService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tutors")

public class TutorController {
    private final TutorService tutorService;

    public TutorController(TutorService tutorService) {
        this.tutorService = tutorService;
    }

    @GetMapping("")
    public ResponseEntity<TutorsForListPaginatedDto> geTutorByPage(Pageable pageable) {
        TutorsForListPaginatedDto response = tutorService.getAllTutors(pageable);
        return ResponseEntity.ok().body(response);
    }
}
