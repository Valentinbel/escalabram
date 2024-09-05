package com.escalabram.escalabram.service.dto;

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
}
