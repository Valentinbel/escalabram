package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.Language;
import com.escalabram.escalabram.repository.LanguageRepository;
import com.escalabram.escalabram.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;

    @Override
    public boolean existById(Long languageId) {
        return languageRepository.existsById(languageId);
    }

    @Override
    public Optional<Language> findById(Long languageId) {
        return languageRepository.findById(languageId);
    }
}
