package com.example.agileboardsserver.controller;

import com.example.agileboardsserver.dto.RegistrationRequest;
import com.example.agileboardsserver.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class SignUpController {

    private final SignUpService signUpService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody RegistrationRequest registrationRequest){
        try{
            signUpService.signUp(registrationRequest);
            return new ResponseEntity<>("User registration successful", CREATED);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), BAD_REQUEST);
        }
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> verifyAccount(@RequestParam String token){
        try{
            signUpService.verifyAccount(token);
            return new ResponseEntity<>("Account activated", OK);
        }catch(Exception exception){
            return new ResponseEntity<>(exception.getMessage(), BAD_REQUEST);
        }
    }
}
