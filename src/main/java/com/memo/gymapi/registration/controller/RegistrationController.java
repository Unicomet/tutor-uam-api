package com.memo.gymapi.registration.controller;
import com.memo.gymapi.registration.requests.RegistrationRequest;
import com.memo.gymapi.registration.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping(value = "registro-comunidad", consumes="application/json")
    public ResponseEntity<Void> register(@RequestBody RegistrationRequest request) {
        try{
            registrationService.register(request);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Error al registrar datos del usuario para univrse a la comunidad");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
