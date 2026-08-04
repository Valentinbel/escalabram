package com.escalabram.escalabram.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SearchClimbLevelDTO implements ISearchClimbLevelDTO {
    private Long searchid;
    private Long climblevelid;
}
