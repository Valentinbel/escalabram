package com.escalabram.escalabram.events.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailMessage implements Serializable {

    private String to;
    private String subject;
    private String body;
    private EmailType type;

    // Métadonnées utiles pour le monitoring / replay
    private String correlationId;   // ID de la requête d'origine
    private LocalDateTime createdAt;

    public enum EmailType {
        WELCOME,
        PASSWORD_RESET,
        ORDER_CONFIRMATION,
        NOTIFICATION
    }
}
