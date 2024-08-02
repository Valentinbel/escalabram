package com.escalabram.escalabram.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name="time_slot")
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_slot", nullable = false)
    private LocalDate dateSlot;

    @Column(name = "begin_time")
    private String beginTime; //LocalTime (?)

    @Column(name = "end_time")
    private String endTime; //LocalTime (?)

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "search_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Search search;

    public TimeSlot() {
    }

    public TimeSlot(Long id, LocalDate dateSlot, String beginTime, String endTime) {
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

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeSlot timeSlot)) return false;
        return Objects.equals(getId(), timeSlot.getId())
                && Objects.equals(getDateSlot(), timeSlot.getDateSlot())
                && Objects.equals(getBeginTime(), timeSlot.getBeginTime())
                && Objects.equals(getEndTime(), timeSlot.getEndTime())
                && Objects.equals(getSearch(), timeSlot.getSearch());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getDateSlot(),
                getBeginTime(),
                getEndTime(),
                getSearch());
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
                "id=" + id +
                ", dateSlot=" + dateSlot +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                '}';
    }
}
