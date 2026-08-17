package com.escalabram.escalabram.utils.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class ThymeleafEmailConfig {
    // Moteur Thymeleaf dédié aux mails autoconfiguré par SpringBoot est orienté web. Moteur standalone propre et isolé.

    @Bean(name = "emailTemplateEngine")
    public TemplateEngine emailTemplateEngine() {
        TemplateEngine engine = new TemplateEngine();
        engine.addTemplateResolver(emailTemplateResolver());
        return engine;
    }

    private ClassLoaderTemplateResolver emailTemplateResolver() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/emails/");   // dossier dédié emails
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCacheable(true);               // cache en prod
        resolver.setOrder(1);
        return resolver;
    }
}
