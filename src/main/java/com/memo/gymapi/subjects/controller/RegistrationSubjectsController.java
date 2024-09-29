package com.memo.gymapi.subjects.controller;

import com.memo.gymapi.subjects.service.RegistrationSubjectsService;
import com.memo.gymapi.subjects.dto.RegistrateScheduleRequest;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/subjects")
public class RegistrationSubjectsController {

    private final RegistrationSubjectsService registrationSubjectsService;

    @PostMapping (consumes="application/json")
    public ResponseEntity<String> register(  @RequestBody RegistrateScheduleRequest request) throws Exception {
        try{
            System.out.println("Registering subjects");
            registrationSubjectsService.registerAvailability(request.getAvailability());
            registrationSubjectsService.registerSubjects(request.getSubjects());
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
