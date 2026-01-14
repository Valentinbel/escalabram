package com.escalabram.escalabram.service.mapper;

import com.escalabram.escalabram.model.Profile;
import com.escalabram.escalabram.service.dto.ProfileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    @Mapping(source = "profile.user.id", target="userId")
    @Mapping(source = "profile.user.userName", target="userName")
    ProfileDTO toProfileDTO(Profile profile);

    @Mapping(source = "dto.userId", target="user.id" )
    @Mapping(source = "dto.userName", target="user.userName")
    Profile toProfile(ProfileDTO dto);
}
