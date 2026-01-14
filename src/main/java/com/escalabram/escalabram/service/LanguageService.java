package com.escalabram.escalabram.service;

import com.escalabram.escalabram.model.Language;

import java.util.Optional;

public interface LanguageService {

    boolean existById(Long languageId);

    Optional<Language> findById(Long languageId);
}
