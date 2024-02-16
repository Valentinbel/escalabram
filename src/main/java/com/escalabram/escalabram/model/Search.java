package com.escalabram.escalabram.model;

import jakarta.persistence.*;

@Entity
@Table(name="search")
public class Search {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "profile_id", nullable = false)
    private Long profileId;

    @Column(name= "min_climbing_level_id")
    private Long minClimbingLevelId;

    @Column(name= "max_climbing_level_id")
    private Long maxClimbingLevelId;

    @Column(name = "have_rope")
    private Boolean haveRope;

    @Column(name = "have_belay_device")
    private Boolean haveBelayDevice;

    @Column(name = "have_quickdraw")
    private Boolean haveQuickdraw;

    @Column(name = "have_car_to_share")
    private Boolean haveCarToShare;

    @Column(name= "time_slot_id")
    private Long timeSlotId;

    @Column(name= "place_id")
    private Long placeId;

    @Column(name = "prefered_gender_id")
    private Long preferedGenderId;

    @Column(name = "search_comment")
    private String searchComment;

    @Column(name = "is_active")
    private Boolean isActive;

    // FIXME champs à ajouter (?)
    //min-max age
    //preferedGender

    public Search() {

    }

    public Search(Long id, Long profileId, Long minClimbingLevelId, Long maxClimbingLevelId, Boolean haveRope,
                  Boolean haveBelayDevice, Boolean haveQuickdraw, Boolean haveCarToShare, Long timeSlotId, Long placeId,
                  Long preferedGenderId, String searchComment, Boolean isActive) {
        this.id = id;
        this.profileId = profileId;
        this.minClimbingLevelId = minClimbingLevelId;
        this.maxClimbingLevelId = maxClimbingLevelId;
        this.haveRope = haveRope;
        this.haveBelayDevice = haveBelayDevice;
        this.haveQuickdraw = haveQuickdraw;
        this.haveCarToShare = haveCarToShare;
        this.timeSlotId = timeSlotId;
        this.placeId = placeId;
        this.preferedGenderId = preferedGenderId;
        this.searchComment = searchComment;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public Long getMinClimbingLevelId() {
        return minClimbingLevelId;
    }

    public void setMinClimbingLevelId(Long minClimbingLevelId) {
        this.minClimbingLevelId = minClimbingLevelId;
    }

    public Long getMaxClimbingLevelId() {
        return maxClimbingLevelId;
    }

    public void setMaxClimbingLevelId(Long maxClimbingLevelId) {
        this.maxClimbingLevelId = maxClimbingLevelId;
    }

    public Boolean getHaveRope() {
        return haveRope;
    }

    public void setHaveRope(Boolean haveRope) {
        this.haveRope = haveRope;
    }

    public Boolean getHaveBelayDevice() {
        return haveBelayDevice;
    }

    public void setHaveBelayDevice(Boolean haveBelayDevice) {
        this.haveBelayDevice = haveBelayDevice;
    }

    public Boolean getHaveQuickdraw() {
        return haveQuickdraw;
    }

    public void setHaveQuickdraw(Boolean haveQuickdraw) {
        this.haveQuickdraw = haveQuickdraw;
    }

    public Boolean getHaveCarToShare() {
        return haveCarToShare;
    }

    public void setHaveCarToShare(Boolean haveCarToShare) {
        this.haveCarToShare = haveCarToShare;
    }

    public Long getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(Long timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    public Long getPreferedGenderId() {
        return preferedGenderId;
    }

    public void setPreferedGenderId(Long preferedGenderId) {
        this.preferedGenderId = preferedGenderId;
    }

    public String getSearchComment() {
        return searchComment;
    }

    public void setSearchComment(String searchComment) {
        this.searchComment = searchComment;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
