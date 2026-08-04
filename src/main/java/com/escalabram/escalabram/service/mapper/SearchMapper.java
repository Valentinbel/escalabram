package com.escalabram.escalabram.service.mapper;

import com.escalabram.escalabram.model.Search;
import com.escalabram.escalabram.model.TimeSlot;
import com.escalabram.escalabram.dto.SearchDTO;
import com.escalabram.escalabram.dto.SearchListDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SearchMapper {

    @Mapping(source = "search.profile.id", target="profileId")
    SearchDTO toSearchDTO(Search search);

    @Mapping(source = "searchDTO.profileId", target="profile.id" )
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "timeSlots", ignore = true)
    Search toSearch(SearchDTO searchDTO);

    @Mapping(source = "profile.id", target="profileId")
    @Mapping(source = "profile.genderId", target="genderId")
    @Mapping(source = "profile.profileDescription", target="profileDescription")
    @Mapping(source = "profile.user.id", target="userId")
    @Mapping(source = "profile.user.userName", target="userName")
    SearchListDTO toSearchListDTO(Search search);

    List<SearchListDTO> toSearchListDTOs(List<Search> searches);

    // Conversion : Set<TimeSlot> -> Set<LocalDateTime>
    default Set<LocalDateTime> map(Set<TimeSlot> timeSlots) {
        if (timeSlots == null) return new HashSet<>();
        return timeSlots.stream()
                .map(this::timeslotToLocalDateTime)
                .collect(Collectors.toSet());
    }

    default LocalDateTime timeslotToLocalDateTime(TimeSlot timeSlot) {
        if (timeSlot == null) return null;
        return timeSlot.getBeginTime();
    }
}
