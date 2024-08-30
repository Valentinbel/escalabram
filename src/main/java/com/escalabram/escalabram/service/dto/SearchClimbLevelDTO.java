package com.escalabram.escalabram.service.dto;

import java.util.Objects;

public class SearchClimbLevelDTO implements ISearchClimbLevelDTO {
    private Long searchid;
    private Long climblevelid;

    public SearchClimbLevelDTO(Long searchid, Long climblevelid) {
        this.searchid = searchid;
        this.climblevelid = climblevelid;
    }

    public Long getSearchid() {
        return searchid;
    }

    public void setSearchid(Long searchid) {
        this.searchid = searchid;
    }

    public Long getClimblevelid() {
        return climblevelid;
    }

    public void setClimblevelid(Long climblevelid) {
        this.climblevelid = climblevelid;
    }

    @Override
    public String toString() {
        return "ClimbLevelDTO{" +
                "searchId=" + searchid +
                ", climbLevelId=" + climblevelid +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SearchClimbLevelDTO that)) return false;
        return Objects.equals(getSearchid(), that.getSearchid()) && Objects.equals(getClimblevelid(), that.getClimblevelid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSearchid(), getClimblevelid());
    }
}
