package com.escalabram.escalabram.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name="climber_profile")
public class ClimberProfile implements Serializable {
    @Serial
    private static final long serialVersionUID = -2789592186380559249L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "profile_name")
    private String profileName;

    // TODO Decider si cette colonne est importante
    //    @Column(name = "birth_date")
    //    private Date birthDate;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "gender_id")
    private Long genderId;

    @Column(name = "language_id")
    private Long languageId;

    @JsonIgnore // TODO enlever ça et mettre un DTO ? ou corriger les parametres ici
    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "climberProfile")
    private ClimberUser climberUser;

    @Column(name = "is_notified", nullable = false)
    private boolean isNotified;

    @Column(columnDefinition = "TEXT", name = "climber_profile_description")
    private String climberProfileDescription;

    // TODO Gerer les relations de table
    //hasOne Gender
    //hasOne Language
    //hasManySearch

    public ClimberProfile() {

    }

    public ClimberProfile(Long id,
                          String profileName,
                          String avatar,
                          Long genderId,
                          Long languageId,
                          ClimberUser climberUser,
                          boolean isNotified,
                          String climberProfileDescription) {
        this.id = id;
        this.profileName = profileName;
        this.avatar = avatar;
        this.genderId = genderId;
        this.languageId = languageId;
        this.climberUser = climberUser;
        this.isNotified = isNotified;
        this.climberProfileDescription = climberProfileDescription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
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

    public ClimberUser getClimberUser() {
        return climberUser;
    }

    public void setClimberUser(ClimberUser climberUser) {
        this.climberUser = climberUser;
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
                ", profileName='" + profileName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", genderId=" + genderId +
                ", languageId=" + languageId +
                ", climberUser=" + climberUser +
                ", isNotified=" + isNotified +
                ", climberProfileDescription='" + climberProfileDescription + '\'' +
                '}';
    }
}
