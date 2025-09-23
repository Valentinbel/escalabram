package com.escalabram.escalabram.service.mapper;

import com.escalabram.escalabram.model.ClimberProfile;
import com.escalabram.escalabram.service.dto.ClimberProfileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClimberProfileMapper {

    @Mapping(target="climberUserId", source = "climberUser.id")
    ClimberProfileDTO toClimberProfileDTO(ClimberProfile climberProfile);

    @Mapping(target="climberUser.id", source = "climberUserId")
    ClimberProfile toClimberProfile(ClimberProfileDTO dto);
}
