package com.escalabram.escalabram.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="time_slot")
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long time_slot_id;

    @Column(name = "date_slot", nullable = false)
    private LocalDate dateSlot;

    @Column(name = "begin_time")
    private LocalTime beginTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    public TimeSlot() {

    }

    public TimeSlot(Long time_slot_id, LocalDate dateSlot, LocalTime beginTime, LocalTime endTime) {
        this.time_slot_id = time_slot_id;
        this.dateSlot = dateSlot;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public Long getTime_slot_id() {
        return time_slot_id;
    }

    public void setTime_slot_id(Long time_slot_id) {
        this.time_slot_id = time_slot_id;
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
