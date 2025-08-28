package com.escalabram.escalabram.service.mapper;

import com.escalabram.escalabram.model.ClimberProfile;
import com.escalabram.escalabram.service.dto.ClimberProfileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ClimberProfileMapper {
    @Mappings({
            @Mapping(target="climberUserId", source = "climberUser.id"),
            //@Mapping(target="avatarId", source = "fileInfo.id")
    })
    ClimberProfileDTO toClimberProfileDTO(ClimberProfile climberProfile);
 // TODO clean
    @Mappings({
            @Mapping(target="climberUser.id", source = "climberUserId"),
            //@Mapping(target="fileInfo.id", source = "avatarId")
    })
    ClimberProfile toClimberProfile(ClimberProfileDTO dto);
}
