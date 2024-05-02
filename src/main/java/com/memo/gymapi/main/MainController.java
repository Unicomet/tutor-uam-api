package com.memo.gymapi.main;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MainController {

    @PostMapping(value = "main")
    public String welcome()
    {
        return "Welcome from secure endpoint";
    }
}