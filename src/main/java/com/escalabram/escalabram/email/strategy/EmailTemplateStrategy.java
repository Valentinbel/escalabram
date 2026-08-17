package com.escalabram.escalabram.email.strategy;

import com.escalabram.escalabram.email.model.EmailMessage;

import java.util.Map;

public interface EmailTemplateStrategy {

    // Le type géré par cette stratégie
    EmailMessage.EmailType getSupportedType();

    String getTemplateName(); // ← nom du fichier Thymeleaf

    // Construit le contenu de l'email à partir du contexte métier
        //    EmailContent buildContent(Map<String, Object> context); // EmailContent va être remplacé:
    Map<String, Object> buildContext(Map<String, Object> data); // ← variables du template
            // le mot Context va être remplacé par data?

    default String getSubject(Map<String, Object> data) {
        return (String) data.getOrDefault("subject", "Notification MyApp");
    } // TODO c'est quoi ça? default ? dans une interface?
}



