package com.escalabram.escalabram.email.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailMessage implements Serializable { // TODO : warning. data n'est pas serialisable. Mais est ce que notre dto doit implementer serialisable?

    @Serial
    private static final long serialVersionUID = -6631868908430223475L;

    private String to;
    private Map<String, Object> data; // TODO data ou context?
    private EmailType type;

    // Métadonnées utiles pour le monitoring / replay
    private String correlationId;   // ID de la requête d'origine
    private LocalDateTime createdAt;

    public enum EmailType { // TODO POurquoi ici
        WELCOME,
        PASSWORD_RESET,
        ORDER_CONFIRMATION,
        NOTIFICATION
    }
}
