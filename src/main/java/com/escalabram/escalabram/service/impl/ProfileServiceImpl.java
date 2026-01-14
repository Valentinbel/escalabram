package com.escalabram.escalabram.service.impl;

import com.escalabram.escalabram.model.Profile;
import com.escalabram.escalabram.repository.ProfileRepository;
import com.escalabram.escalabram.service.ProfileService;
import com.escalabram.escalabram.service.UserService;
import com.escalabram.escalabram.service.dto.ProfileDTO;
import com.escalabram.escalabram.service.mapper.ProfileMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private static final Logger log = LoggerFactory.getLogger(ProfileServiceImpl.class);
    private final UserService userService;
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Override
    public Optional<ProfileDTO> findByUserId(Long userId) {
        log.debug("Profile findByUserId : {}", userId);
        Optional<Profile> optionalProfile = profileRepository.findByUserId(userId);
        if(optionalProfile.isPresent()) {
            ProfileDTO profileDTO = this.profileMapper.toProfileDTO(optionalProfile.get());
            return Optional.ofNullable(profileDTO);
        } else return  Optional.empty();
    }

    @Override
    public boolean existsById(Long profileId) {
        return profileRepository.existsById(profileId);
    }

    @Override
    public ProfileDTO saveProfile(ProfileDTO profileDTO) {
        log.debug("ProfileRequestDTO : {}", profileDTO);
        try {
            if (profileDTO.getUserId() != null &&  profileDTO.getUserName() != null)
                userService.updateUserNameById(profileDTO.getUserId(), profileDTO.getUserName());

            Profile profile = this.profileMapper.toProfile(profileDTO);

            Profile savedProfile = profileRepository.save(profile);
            return this.profileMapper.toProfileDTO(savedProfile);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Error thrown trying to save profile with userId: " + profileDTO.getUserId());
        }
    }
}
