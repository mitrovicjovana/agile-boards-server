package com.example.agileboardsserver.service;

import com.example.agileboardsserver.dto.Mail;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    @Async
    public void sendConfirmationMail(Mail mail){
        System.out.println("Mail sent");
    }

}
