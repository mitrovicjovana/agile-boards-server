package com.example.agileboardsserver.controller;

import com.example.agileboardsserver.dto.RegistrationRequest;
import com.example.agileboardsserver.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody RegistrationRequest registrationRequest){
        try{
            authService.signUp(registrationRequest);
            return new ResponseEntity<>("User registration successful", CREATED);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), BAD_REQUEST);
        }
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> verifyAccount(@RequestParam String token){
        try{
            authService.verifyAccount(token);
            return new ResponseEntity<>("Account activated", OK);
        }catch(Exception exception){
            return new ResponseEntity<>(exception.getMessage(), BAD_REQUEST);
        }
    }
}
