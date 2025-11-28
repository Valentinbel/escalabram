package com.escalabram.escalabram.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name="time_slot")
public class TimeSlot implements Serializable {

    @Serial
    private static final long serialVersionUID = -3149722237167848310L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @FutureOrPresent(message = "BeginTime needs to be present or future")
    @Column(name = "begin_time") //, columnDefinition = "TIME"
    private Timestamp beginTime;

    @NotBlank
    @FutureOrPresent(message = "EndTime needs to be present or future")
    @Column(name = "end_time") //, columnDefinition = "TIME"
    private Timestamp  endTime;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "search_id" , referencedColumnName = "id")// , foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    private Search search;
}
