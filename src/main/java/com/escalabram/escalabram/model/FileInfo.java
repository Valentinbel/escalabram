package com.escalabram.escalabram.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name="file_info",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = { "name", "url" })})
public class FileInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1932734488789463352L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @Column(name = "url", nullable = false)
    private String url;

    @NotNull
    @OneToOne
    @JoinColumn(name = "climber_user_id", referencedColumnName = "id", nullable = false)
    private ClimberUser climberUser;

    public FileInfo() {
    }

    public FileInfo(Long id, String name, String url, ClimberUser climberUser) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.climberUser = climberUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ClimberUser getClimberUser() {
        return climberUser;
    }

    public void setClimberUser(ClimberUser climberUser) {
        this.climberUser = climberUser;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", climberUser=" + climberUser +
                '}';
    }
}
