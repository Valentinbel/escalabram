package com.escalabram.escalabram.service.mapper;

import com.escalabram.escalabram.model.ClimberProfile;
import com.escalabram.escalabram.service.dto.ClimberProfileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClimberProfileMapper {

    // On mappe tout le DTO pour éviter les warnings à la compilation
    @Mapping(source = "climberProfile.climberUser.id", target="climberUserId")
    @Mapping(source = "climberProfile.climberUser.userName", target="userName")
    @Mapping(source = "climberProfile.climberUser.fileInfo.id", target="avatarId")
    ClimberProfileDTO toClimberProfileDTO(ClimberProfile climberProfile);

    @Mapping(source = "dto.climberUserId", target="climberUser.id" )
    @Mapping(source = "dto.userName", target="climberUser.userName")
    @Mapping(source = "dto.avatarId", target="climberUser.fileInfo.id")
    ClimberProfile toClimberProfile(ClimberProfileDTO dto);
}
