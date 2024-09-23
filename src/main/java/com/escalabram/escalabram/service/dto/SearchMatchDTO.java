package com.escalabram.escalabram.service.dto;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

public class SearchMatchDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -7787655194340375671L;

    private Long searchId;
    private Long timeSlotId;
    private Timestamp beginTime;
    private Timestamp endTime;

    public SearchMatchDTO() {
    }
    public SearchMatchDTO(Long searchId, Long timeSlotId, Timestamp beginTime, Timestamp endTime) {
        this.searchId = searchId;
        this.timeSlotId = timeSlotId;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public Long getSearchId() {
        return searchId;
    }

    public void setSearchId(Long searchId) {
        this.searchId = searchId;
    }

    public Long getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(Long timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public Timestamp getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Timestamp beginTime) {
        this.beginTime = beginTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "SearchForMatchDTO{" +
                "searchId=" + searchId +
                ", timeSlotId=" + timeSlotId +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                '}';
    }
}
