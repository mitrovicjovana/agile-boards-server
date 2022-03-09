package com.example.agileboardsserver.service;

import com.example.agileboardsserver.dto.Mail;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    // TODO: Add HTML template for mail

    private final JavaMailSender mailSender;

    @Async
    public void sendConfirmationMail(Mail mail){
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

            messageHelper.setFrom("agile.boards@mail.com");
            messageHelper.setTo(mail.getRecipient());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mail.getBody());
        };

        try{
            mailSender.send(messagePreparator);
        }catch (MailException exception){
            throw new RuntimeException("Problem occurred while sending confirmation mail to " + mail.getRecipient());
        }
    }

}
