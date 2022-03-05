package com.example.agileboardsserver.service;

import com.example.agileboardsserver.dto.RegistrationRequest;
import com.example.agileboardsserver.model.User;
import com.example.agileboardsserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public void signUp(RegistrationRequest registrationRequest){
        boolean usernameExists =  userRepository.findByUsername(registrationRequest.getUsername()).isPresent();
        boolean emailExists = userRepository.findByEmail(registrationRequest.getEmail()).isPresent();

        if(usernameExists){
            throw new IllegalStateException("Username " + registrationRequest.getUsername() + " is already taken.");
        }else if(emailExists){
            throw new IllegalStateException("Email " + registrationRequest.getEmail() + "is already in use.");
        }else{
            saveNewUser(registrationRequest);
        }
    }

    private void saveNewUser(RegistrationRequest registrationRequest) {
        User user = new User();

        user.setFirstName(registrationRequest.getFirstName());
        user.setLastName(registrationRequest.getLastName());
        user.setUsername(registrationRequest.getUsername());
        user.setEmail(registrationRequest.getEmail());
        user.setEnabled(false);
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

        userRepository.save(user);
    }
}
