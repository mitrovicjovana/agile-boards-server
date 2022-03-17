package com.example.agileboardsserver.service;

import com.example.agileboardsserver.dto.RegistrationRequest;
import com.example.agileboardsserver.model.ConfirmationToken;
import com.example.agileboardsserver.model.User;
import com.example.agileboardsserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Transactional
    public void enableUser(ConfirmationToken token) {
        // Enable user
        String username = token.getUser().getUsername();
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " is not found"));

        user.setIsEnabled(true);
        userRepository.save(user);

        // Delete token
        confirmationTokenService.deleteToken(token);
    }

    public User saveNewUser(RegistrationRequest registrationRequest) {
        User user = new User();

        user.setFirstName(registrationRequest.getFirstName());
        user.setLastName(registrationRequest.getLastName());
        user.setUsername(registrationRequest.getUsername());
        user.setEmail(registrationRequest.getEmail());
        user.setIsEnabled(false);
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
