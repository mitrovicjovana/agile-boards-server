package com.example.agileboardsserver.service;

import com.example.agileboardsserver.model.ConfirmationToken;
import com.example.agileboardsserver.model.User;
import com.example.agileboardsserver.repository.ConfirmationTokenRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ConfirmationTokenServiceTest {

    @Mock
    private ConfirmationTokenRepository confirmationTokenRepository;

    @InjectMocks
    private ConfirmationTokenService underTest;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        underTest = new ConfirmationTokenService(confirmationTokenRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void itShouldFindTokenByTokenValue() {
        String tokenValue = UUID.randomUUID().toString();
        User user = new User(
                null,
                "Name",
                "Surname",
                "Username",
                "mail@mail.com",
                "password",
                true
        );
        ConfirmationToken token = new ConfirmationToken(
                null,
                tokenValue,
                LocalDateTime.now(),
                user
        );

        Optional<ConfirmationToken> tokenOptional = Optional.of(token);

        when(confirmationTokenRepository.findByToken(tokenValue)).thenReturn(tokenOptional);

        assertEquals(token, underTest.findByToken(tokenValue).get());
    }

}