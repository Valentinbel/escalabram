package com.escalabram.escalabram.service.mapper;

import com.escalabram.escalabram.model.Search;
import com.escalabram.escalabram.service.dto.SearchDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SearchMapper {

    @Mapping(source = "search.profile.id", target="profileId")
    SearchDTO toSearchDTO(Search search);

    @Mapping(source = "searchDTO.profileId", target="profile.id" )
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "title", ignore = true) //todo comment ??
    Search toSearch(SearchDTO searchDTO);
}
