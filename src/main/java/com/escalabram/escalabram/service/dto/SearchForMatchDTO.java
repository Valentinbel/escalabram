package com.escalabram.escalabram.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class SearchForMatchDTO implements Serializable {
    private Long searchId;
    private Timestamp beginTime;
    private Timestamp  endTime;

    public SearchForMatchDTO() {
    }
    public SearchForMatchDTO(Long searchId, Timestamp beginTime, Timestamp endTime) {
        this.searchId = searchId;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public Long getSearchId() {
        return searchId;
    }

    public void setSearchId(Long searchId) {
        this.searchId = searchId;
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
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                '}';
    }
}
