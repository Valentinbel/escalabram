package com.escalabram.escalabram.service.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serial;
import java.io.Serializable;

public class ClimberProfileDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -2061960641588724813L;

    @NotBlank
    private String profileName;

    private String avatar;

    private Long genderId;

    private Long languageId;

    private Boolean isNotified;

    private String climberProfileDescription;

    private Long climberUserId;

    public ClimberProfileDTO() {
    }

    public ClimberProfileDTO(String profileName,
                             String avatar,
                             Long genderId,
                             Long languageId, Boolean isNotified, String climberProfileDescription,
                             Long climberUserId) {
        this.profileName = profileName;
        this.avatar = avatar;
        this.genderId = genderId;
        this.languageId = languageId;
        this.isNotified = isNotified;
        this.climberProfileDescription = climberProfileDescription;
        this.climberUserId = climberUserId;
    }

    public @NotBlank String getProfileName() {
        return profileName;
    }

    public void setProfileName(@NotBlank String profileName) {
        this.profileName = profileName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getGenderId() {
        return genderId;
    }

    public void setGenderId(Long genderId) {
        this.genderId = genderId;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    public Boolean getNotified() {
        return isNotified;
    }

    public void setNotified(Boolean notified) {
        isNotified = notified;
    }

    public String getClimberProfileDescription() {
        return climberProfileDescription;
    }

    public void setClimberProfileDescription(String climberProfileDescription) {
        this.climberProfileDescription = climberProfileDescription;
    }

    public Long getClimberUserId() {
        return climberUserId;
    }

    public void setClimberUserId(Long climberUserId) {
        this.climberUserId = climberUserId;
    }

    @Override
    public String toString() {
        return "ClimberProfileDTO{" +
                "profileName='" + profileName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", genderId=" + genderId +
                ", languageId=" + languageId +
                ", isNotified=" + isNotified +
                ", climberProfileDescription='" + climberProfileDescription + '\'' +
                ", climberUserId=" + climberUserId +
                '}';
    }
}
