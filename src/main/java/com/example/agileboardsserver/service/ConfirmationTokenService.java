package com.example.agileboardsserver.service;

import com.example.agileboardsserver.model.ConfirmationToken;
import com.example.agileboardsserver.model.User;
import com.example.agileboardsserver.repository.ConfiramtionTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {

    private final ConfiramtionTokenRepository confiramtionTokenRepository;

    public ConfirmationToken generateConfirmationToken(User user) {
        // Generate new token
        ConfirmationToken confirmationToken = new ConfirmationToken();

        confirmationToken.setToken(UUID.randomUUID().toString());
        confirmationToken.setCreatedAt(LocalDateTime.now());
        confirmationToken.setUser(user);

        // Save token
        return confiramtionTokenRepository.save(confirmationToken);
    }

    public Optional<ConfirmationToken> findByToken(String token) {
        return confiramtionTokenRepository.findByToken(token);
    }

    public void deleteToken(ConfirmationToken confirmationToken) {
        confiramtionTokenRepository.delete(confirmationToken);
    }
}
