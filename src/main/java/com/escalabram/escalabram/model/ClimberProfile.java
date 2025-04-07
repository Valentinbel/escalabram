package com.escalabram.escalabram.model;

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

    @OneToOne
    @JoinColumn(name = "file_info_id", referencedColumnName = "id")
    private FileInfo fileInfo;

    @Column(name = "gender_id")
    private Long genderId;

    @Column(name = "language_id")
    private Long languageId;

    @Column(name = "is_notified", nullable = false)
    private Boolean isNotified;

    @Column(columnDefinition = "TEXT", name = "climber_profile_description")
    private String climberProfileDescription;

    @OneToOne//(cascade = CascadeType.ALL) //fetch = FetchType.LAZY ?
    @JoinColumn(name = "climber_user_id", referencedColumnName = "id", nullable = false)
    private ClimberUser climberUser;

    // TODO Gerer les relations de table
    //hasOne Gender
    //hasOne Language
    //hasManySearch ==> Comprendre les différents types de Cascade et autres parametres

    public ClimberProfile() {

    }

    public ClimberProfile(Long id,
                          Long genderId,
                          Long languageId,
                          ClimberUser climberUser,
                          Boolean isNotified,
                          String climberProfileDescription) {
        this.id = id;
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

    public Boolean isNotified() {
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

    @Override
    public String toString() {
        return "ClimberProfile{" +
                "id=" + id +
                ", genderId=" + genderId +
                ", languageId=" + languageId +
                ", climberUser=" + climberUser +
                ", isNotified=" + isNotified +
                ", climberProfileDescription='" + climberProfileDescription + '\'' +
                '}';
    }
}
