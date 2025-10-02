package com.escalabram.escalabram.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name="file_info",
        uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "url" })})
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
}
