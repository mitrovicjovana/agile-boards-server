package com.example.agileboardsserver.controller;

import com.example.agileboardsserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/exist/email/{email}")
    public ResponseEntity<String> existsByEmail(@PathVariable String email) {
        return new ResponseEntity<>(userService.existsByEmail(email).toString(), OK);
    }

    @GetMapping("/exist/username/{username}")
    public ResponseEntity<String> existsByUsername(@PathVariable String username) {
        return new ResponseEntity<>(userService.existsByUsername(username).toString(), OK);
    }

    @GetMapping("/username")
    public ResponseEntity<String> getUsername() {
        return new ResponseEntity<>(SecurityContextHolder.getContext().getAuthentication().getName(), OK);
    }

}
