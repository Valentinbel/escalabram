package com.escalabram.escalabram.service.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SearchMatchDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -7787655194340375671L;

    private Long searchId;
    private Long timeSlotId;
    private Timestamp beginTime;
    private Timestamp endTime;
}
