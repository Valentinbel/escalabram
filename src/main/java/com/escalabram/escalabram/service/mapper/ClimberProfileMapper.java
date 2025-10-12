package com.escalabram.escalabram.service.mapper;

import com.escalabram.escalabram.model.ClimberProfile;
import com.escalabram.escalabram.service.dto.ClimberProfileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClimberProfileMapper {

    @Mapping(source = "climberProfile.climberUser.id", target="climberUserId")
    @Mapping(source = "climberProfile.climberUser.userName", target="userName")
    ClimberProfileDTO toClimberProfileDTO(ClimberProfile climberProfile);

    @Mapping(source = "dto.climberUserId", target="climberUser.id" )
    @Mapping(source = "dto.userName", target="climberUser.userName")
    ClimberProfile toClimberProfile(ClimberProfileDTO dto);
}
