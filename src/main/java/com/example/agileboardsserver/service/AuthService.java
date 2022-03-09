package com.example.agileboardsserver.service;

import com.example.agileboardsserver.dto.LoginRequest;
import com.example.agileboardsserver.dto.Mail;
import com.example.agileboardsserver.dto.RegistrationRequest;
import com.example.agileboardsserver.model.ConfirmationToken;
import com.example.agileboardsserver.model.User;
import com.example.agileboardsserver.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Transactional
    public void signUp(RegistrationRequest registrationRequest) {
        String confirmationURL = "http://localhost:8080/api/auth/confirm?token=";

        // Check if username or email are already in use
        boolean usernameExists = userService.findByUsername(registrationRequest.getUsername()).isPresent();
        boolean emailExists = userService.findByEmail(registrationRequest.getEmail()).isPresent();

        if (usernameExists) {
            throw new IllegalStateException("Username " + registrationRequest.getUsername() + " is already taken.");
        } else if (emailExists) {
            throw new IllegalStateException("Email " + registrationRequest.getEmail() + " is already in use.");
        } else {
            // Save new user to database
            User user = userService.saveNewUser(registrationRequest);

            // Generate and save token for given user
            ConfirmationToken token = confirmationTokenService.generateConfirmationToken(user);

            // Send confirmation email
            mailService.sendConfirmationMail(new Mail("Please activate your account",
                    registrationRequest.getEmail(),
                    "Click on the link below to activate your account: " + confirmationURL + token.getToken()));

        }
    }

    public String login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtProvider.generateJwtToken(authentication);
    }

    public void verifyAccount(String token) {
        Optional<ConfirmationToken> confirmationToken = confirmationTokenService.findByToken(token);
        confirmationToken.orElseThrow(() -> new RuntimeException("Invalid token"));
        userService.enableUser(confirmationToken.get());
    }
}
