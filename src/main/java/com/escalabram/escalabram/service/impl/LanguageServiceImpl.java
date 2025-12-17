package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.repository.LanguageRepository;
import com.escalabram.escalabram.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;

    @Override
    public boolean existById(Long languageId) {
        return languageRepository.existsById(languageId);
    }
}
