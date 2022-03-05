package com.example.agileboardsserver.controller;

import com.example.agileboardsserver.dto.RegistrationRequest;
import com.example.agileboardsserver.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody RegistrationRequest registrationRequest){
        authService.signUp(registrationRequest);
        return new ResponseEntity<>("User registration successful", CREATED);
    }
}
