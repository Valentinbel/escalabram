package com.escalabram.escalabram.service.mapper;

import com.escalabram.escalabram.model.Search;
import com.escalabram.escalabram.model.TimeSlot;
import com.escalabram.escalabram.service.dto.SearchDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SearchMapper {

    @Mapping(source = "search.profile.id", target="profileId")
    SearchDTO toSearchDTO(Search search);

    @Mapping(source = "searchDTO.profileId", target="profile.id" )
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "timeSlots", ignore = true)
    @Mapping(target = "title", ignore = true) //todo comment ??
    Search toSearch(SearchDTO searchDTO);


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
