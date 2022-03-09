package com.example.agileboardsserver.controller;

import com.example.agileboardsserver.dto.LoginRequest;
import com.example.agileboardsserver.dto.RegistrationRequest;
import com.example.agileboardsserver.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody RegistrationRequest registrationRequest) {
        try {
            authService.signUp(registrationRequest);
            return new ResponseEntity<>("User registration successful", CREATED);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), BAD_REQUEST);
        }
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> verifyAccount(@RequestParam String token) {
        try {
            authService.verifyAccount(token);
            return new ResponseEntity<>("Account activated", OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    private ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(authService.login(loginRequest), OK);
    }
}
