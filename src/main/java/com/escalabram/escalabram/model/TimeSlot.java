package com.escalabram.escalabram.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="time_slot")
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_slot", nullable = false)
    private LocalDate dateSlot;

    @Column(name = "begin_time")
    private LocalTime beginTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    public TimeSlot() {

    }

    public TimeSlot(Long id, LocalDate dateSlot, LocalTime beginTime, LocalTime endTime) {
        this.id = id;
        this.dateSlot = dateSlot;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateSlot() {
        return dateSlot;
    }

    public void setDateSlot(LocalDate dateSlot) {
        this.dateSlot = dateSlot;
    }

    public LocalTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
