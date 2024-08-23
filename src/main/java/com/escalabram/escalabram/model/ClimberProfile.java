package com.escalabram.escalabram.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="climber_profile")
public class ClimberProfile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // TODO Decider si cette colonne est importante
    //    @Column(name = "birth_date")
    //    private Date birthDate;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "gender_id")
    private Long genderId;

    @Column(name = "language_id")
    private String languageId;

    @Column(name = "climber_user_id", nullable = false)
    private Long climberUserId;

    @Column(name = "is_notified", nullable = false)
    private boolean isNotified;

    @Column(name = "climber_profile_description")
    private String climberProfileDescription;

    // TODO Gerer les relations de table
    //hasOne Gender
    //hasOne Language
    //hasManySearch

    public ClimberProfile() {

    }

    public ClimberProfile(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String avatar, Long genderId,
                          String languageId, Long climberUserId, boolean isNotified, String climberProfileDescription) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.avatar = avatar;
        this.genderId = genderId;
        this.languageId = languageId;
        this.climberUserId = climberUserId;
        this.isNotified = isNotified;
        this.climberProfileDescription = climberProfileDescription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
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

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public Long getClimberUserId() {
        return climberUserId;
    }

    public void setClimberUserId(Long climberUserId) {
        this.climberUserId = climberUserId;
    }

    public boolean isNotified() {
        return isNotified;
    }

    public void setNotified(boolean notified) {
        isNotified = notified;
    }

    public String getClimberProfileDescription() {
        return climberProfileDescription;
    }

    public void setClimberProfileDescription(String climberProfileDescription) {
        this.climberProfileDescription = climberProfileDescription;
    }

    @Override
    public String toString() {
        return "ClimberProfile{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", avatar='" + avatar + '\'' +
                ", genderId=" + genderId +
                ", languageId='" + languageId + '\'' +
                ", climberUserId=" + climberUserId +
                ", isNotified=" + isNotified +
                ", climberProfileDescription='" + climberProfileDescription + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClimberProfile that)) return false;
        return isNotified() == that.isNotified() && Objects.equals(getId(), that.getId()) && Objects.equals(getCreatedAt(), that.getCreatedAt()) && Objects.equals(getUpdatedAt(), that.getUpdatedAt()) && Objects.equals(getAvatar(), that.getAvatar()) && Objects.equals(getGenderId(), that.getGenderId()) && Objects.equals(getLanguageId(), that.getLanguageId()) && Objects.equals(getClimberUserId(), that.getClimberUserId()) && Objects.equals(getClimberProfileDescription(), that.getClimberProfileDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCreatedAt(), getUpdatedAt(), getAvatar(), getGenderId(), getLanguageId(), getClimberUserId(), isNotified(), getClimberProfileDescription());
    }
}
