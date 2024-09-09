package com.escalabram.escalabram.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="climber_profile")
public class ClimberProfile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "profil_name")
    private String profilName;

    // TODO Decider si cette colonne est importante
    //    @Column(name = "birth_date")
    //    private Date birthDate;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "gender_id")
    private Long genderId;

    @Column(name = "language_id")
    private Long languageId;

    @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "climberProfile")
    private ClimberUser climberUser;

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

    public ClimberProfile(Long id,
                          String profilName,
                          String avatar,
                          Long genderId,
                          Long languageId,
                          ClimberUser climberUser,
                          boolean isNotified,
                          String climberProfileDescription) {
        this.id = id;
        this.profilName = profilName;
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

    public String getProfilName() {
        return profilName;
    }

    public void setProfilName(String profilName) {
        this.profilName = profilName;
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
                ", profilName='" + profilName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", genderId=" + genderId +
                ", languageId=" + languageId +
                ", climberUser=" + climberUser +
                ", isNotified=" + isNotified +
                ", climberProfileDescription='" + climberProfileDescription + '\'' +
                '}';
    }
}
