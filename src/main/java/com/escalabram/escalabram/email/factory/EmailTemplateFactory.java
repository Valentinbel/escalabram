package com.escalabram.escalabram.email.factory;

import com.escalabram.escalabram.email.model.EmailMessage;
import com.escalabram.escalabram.email.strategy.EmailTemplateStrategy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class EmailTemplateFactory {

    private final Map<EmailMessage.EmailType, EmailTemplateStrategy> strategies;

    // Spring injecte automatiquement toutes les implémentations de EmailTemplateStrategy
    public EmailTemplateFactory(List<EmailTemplateStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap( // TODO expliquer ce qu'il se passe ici
                        EmailTemplateStrategy::getSupportedType,
                        Function.identity()
                ));
    }

    public EmailTemplateStrategy resolve(EmailMessage.EmailType type) {
        EmailTemplateStrategy strategy = strategies.get(type);

        if (strategy == null) {
            throw new IllegalArgumentException(
                    "Aucune stratégie trouvée pour le type : " + type
            );
        }
        return strategy;
    }
}
