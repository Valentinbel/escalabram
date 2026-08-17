package com.escalabram.escalabram.email.strategy;

import com.escalabram.escalabram.email.model.EmailMessage;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class WelcomeEmailStrategy implements EmailTemplateStrategy {
    @Override
    public EmailMessage.EmailType getSupportedType() {
        return EmailMessage.EmailType.WELCOME;
    }

    @Override
    public String getTemplateName() {
        return "welcome"; // → templates/emails/welcome.html
    }

    @Override
    public String getSubject(Map<String, Object> data) {
        return "Bienvenue sur Pinya, " + data.get("name") + " !";
    }

    @Override
    public Map<String, Object> buildContext(Map<String, Object> data) {
        // Validation, transformation, valeurs par défaut...
        return Map.of(
                "name",           data.get("name"),
                "email",          data.get("email"),
                "loginUrl",       data.getOrDefault("loginUrl", "https://myapp.com/login"),
                "createdAt",      LocalDateTime.now(),
                "unsubscribeUrl", buildUnsubscribeUrl(data.get("email"))
        );
    }

    private String buildUnsubscribeUrl(Object email) {
        return "https://myapp.com/unsubscribe?email=" + email; // TODO : A configurer
    }


//    @Override
//    public EmailContent buildContent(Map<String, Object> context) { // TODO Map<String, Object>
//        String name = (String) context.get("name"); // paramètres necessaires dans le mail
//
//        return new EmailContent( // TODO a changer avec Thymeleaf
//                "Bienvenue sur notre plateforme, " + name + " !",
//                """
//                        <h1>Bonjour %s 👋</h1>
//                        <p>Votre compte a été créé avec succès.</p>
//                        <a href="%s">Accéder à mon espace</a>
//                        """.formatted(name, context.get("loginUrl"))
//        );
//    }
}
