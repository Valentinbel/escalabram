package com.escalabram.escalabram.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -2061960641588724813L;

    private Long id;

    @NotBlank
    private String userName;

    private Long genderId;

    private Boolean isNotified;// TODO A supprimer ?

    private String profileDescription;

    @NotNull
    private Long userId;
}
