package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.email.factory.EmailTemplateFactory;
import com.escalabram.escalabram.email.model.EmailMessage;
import com.escalabram.escalabram.email.strategy.EmailTemplateStrategy;
import com.escalabram.escalabram.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService { // TODO où le placer? service>email>EmailServiceImpl || email>emailService ??

    private final JavaMailSender mailSender;
    private final EmailTemplateFactory templateFactory;

    @Qualifier("emailTemplateEngine")
    private final TemplateEngine templateEngine; // ← notre moteur dédié

    @Value("${spring.mail.username}")
    private String fromAddress;

    @Override
    public void send(EmailMessage emailMessage) {

        // 0. Résolution de la dynamique de la stratégy
        EmailTemplateStrategy strategy = templateFactory.resolve(emailMessage.getType());

        // 1. Construction du contexte Thymeleaf
        Context thymeleafContext = new Context(Locale.FRENCH); // TODO J'imagine qu'on pourra faire un st des langues ici
        thymeleafContext.setVariables(strategy.buildContext(emailMessage.getData()));

        // 2. Rendu HTML par Thymeleaf
        String htmlBody = templateEngine.process(strategy.getTemplateName(), thymeleafContext);

        // 3. Envoi SMTP
        sendMimeMessage(
                emailMessage.getTo(),
                strategy.getSubject(emailMessage.getData()),
                htmlBody
        );
    }

    private void sendMimeMessage(String to, String subject, String htmlBody) {
        try {
            MimeMessage mime = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mime, true, "UTF-8");
            helper.setFrom(fromAddress);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // true = HTML
            mailSender.send(mime);
            log.info("Email envoyé à {}", to);
        } catch (MessagingException e) {
            throw new MailSendException("Erreur lors de l'envoi de l'email à " + to, e);
        }
    }
}
