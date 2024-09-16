package com.escalabram.escalabram.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name="time_slot")
public class TimeSlot implements Serializable {

    @Serial
    private static final long serialVersionUID = -3149722237167848310L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "begin_time") //, columnDefinition = "TIME"
    private Timestamp beginTime;

    @Column(name = "end_time") //, columnDefinition = "TIME"
    private Timestamp  endTime;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "search_id" , referencedColumnName = "id")// , foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    private Search search;

    public TimeSlot() {
    }

    public TimeSlot(Long id, Timestamp  beginTime, Timestamp  endTime) {
        this.id = id;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp  getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Timestamp  beginTime) {
        this.beginTime = beginTime;
    }

    public Timestamp  getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp  endTime) {
        this.endTime = endTime;
    }

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
                "id=" + id +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                '}';
    }
}
