package com.escalabram.escalabram.service.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ClimberProfileDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -2061960641588724813L;

    private Long id;

    private String userName;

    private Long genderId;

    private Long languageId;

    private Boolean isNotified;

    private String climberProfileDescription;

    private Long climberUserId;
}
