package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.email.model.EmailMessage;
import com.escalabram.escalabram.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromAddress;

    @Override
    public void send(EmailMessage emailMessage) {
        MimeMessage mime = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mime, true, "UTF-8");
            helper.setFrom(fromAddress);
            helper.setTo(emailMessage.getTo());
            helper.setSubject(emailMessage.getSubject());
            helper.setText(emailMessage.getBody(), true); // true = HTML

            mailSender.send(mime);

        } catch (MessagingException e) {
            throw new MailSendException("Erreur lors de l'envoi de l'email", e);
        }
    }
}
