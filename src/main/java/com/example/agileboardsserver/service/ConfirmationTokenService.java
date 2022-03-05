package com.example.agileboardsserver.service;

import com.example.agileboardsserver.model.ConfirmationToken;
import com.example.agileboardsserver.model.User;
import com.example.agileboardsserver.repository.ConfiramtionTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {

    private final ConfiramtionTokenRepository confiramtionTokenRepository;

    public ConfirmationToken generateConfirmationToken(User user) {
        ConfirmationToken confirmationToken = new ConfirmationToken();

        confirmationToken.setToken(UUID.randomUUID().toString());
        confirmationToken.setCreatedAt(LocalDateTime.now());
        confirmationToken.setExpiresAt(LocalDateTime.now().plusMinutes(60));
        confirmationToken.setUser(user);

        return confiramtionTokenRepository.save(confirmationToken);
    }
}
