package com.example.agileboardsserver.service;

import com.example.agileboardsserver.dto.RegistrationRequest;
import com.example.agileboardsserver.model.ConfirmationToken;
import com.example.agileboardsserver.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;

    @Transactional
    public void signUp(RegistrationRequest registrationRequest){
        // Check if username or email are already in use
        boolean usernameExists =  userService.findByUsername(registrationRequest.getUsername()).isPresent();
        boolean emailExists = userService.findByEmail(registrationRequest.getEmail()).isPresent();

        if(usernameExists){
            throw new IllegalStateException("Username " + registrationRequest.getUsername() + " is already taken.");
        }else if(emailExists){
            throw new IllegalStateException("Email " + registrationRequest.getEmail() + " is already in use.");
        }else{
            // Save new user to database
            User user = userService.saveNewUser(registrationRequest);

            // Generate and save token for given user
            ConfirmationToken token = confirmationTokenService.generateConfirmationToken(user);

            // Send confirmation email
        }
    }
}
