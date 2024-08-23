package com.escalabram.escalabram.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name="search")
public class Search implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "climber_profile_id", nullable = false)
    private Long climberProfileId;

    @Column(name = "title")
    private String title;

    @Column(name = "have_rope")
    private Boolean haveRope;

    @Column(name = "have_belay_device")
    private Boolean haveBelayDevice;

    @Column(name = "have_quickdraw")
    private Boolean haveQuickdraw;

    @Column(name = "have_car_to_share")
    private Boolean haveCarToShare;

    @Column(name= "place_id")
    private Long placeId;

    @Column(name = "prefered_gender_id")
    private Long preferedGenderId;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "search", cascade = CascadeType.ALL)
    private List<TimeSlot> timeSlots = new ArrayList<>();

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinTable(
            name = "search_climb_level",
            joinColumns = { @JoinColumn(name = "search_id") },
            inverseJoinColumns = { @JoinColumn(name = "climb_level_id") }
    )
    private Set<ClimbLevel> climbLevels = new HashSet<>();

    // TODO champs à ajouter (?)
    //min-max age

    public Search() {

    }

    public Search(Long id, Long climberProfileId,
                  String title,Boolean haveRope,
                  Boolean haveBelayDevice, Boolean haveQuickdraw,
                  Boolean haveCarToShare, Long placeId,
                  Long preferedGenderId,
                  Set<ClimbLevel> climbLevels,
                  Boolean isActive) {
        this.id = id;
        this.climberProfileId = climberProfileId;
        this.title = title;
        this.haveRope = haveRope;
        this.haveBelayDevice = haveBelayDevice;
        this.haveQuickdraw = haveQuickdraw;
        this.haveCarToShare = haveCarToShare;
        this.placeId = placeId;
        this.preferedGenderId = preferedGenderId;
        this.climbLevels = climbLevels;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClimberProfileId() {
        return climberProfileId;
    }

    public void setClimberProfileId(Long climberProfileId) {
        this.climberProfileId = climberProfileId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }

    public Set<ClimbLevel> getClimbLevels() {
        return climbLevels;
    }

    public void setClimbLevels(Set<ClimbLevel> climbLevels) {
        this.climbLevels = climbLevels;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Search search)) return false;
        return Objects.equals(getId(), search.getId())
                && Objects.equals(getClimberProfileId(), search.getClimberProfileId())
                && Objects.equals(getTitle(), search.getTitle())
                && Objects.equals(getHaveRope(), search.getHaveRope())
                && Objects.equals(getHaveBelayDevice(), search.getHaveBelayDevice())
                && Objects.equals(getHaveQuickdraw(), search.getHaveQuickdraw())
                && Objects.equals(getHaveCarToShare(), search.getHaveCarToShare())
                && Objects.equals(getPlaceId(), search.getPlaceId())
                && Objects.equals(getPreferedGenderId(), search.getPreferedGenderId())
               // && Objects.equals(getTimeSlots(), search.getTimeSlots())
                && Objects.equals(getClimbLevels(), search.getClimbLevels())
                && Objects.equals(isActive, search.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getClimberProfileId(),
                getTitle(),
                getHaveRope(),
                getHaveBelayDevice(),
                getHaveQuickdraw(),
                getHaveCarToShare(),
                getPlaceId(),
                getPreferedGenderId(),
                //getTimeSlots(),
                getClimbLevels(),
                isActive);
    }

    @Override
    public String toString() {
        return "Search{" +
                "id=" + id +
                ", climberProfileId=" + climberProfileId +
                ", title='" + title + '\'' +
                ", haveRope=" + haveRope +
                ", haveBelayDevice=" + haveBelayDevice +
                ", haveQuickdraw=" + haveQuickdraw +
                ", haveCarToShare=" + haveCarToShare +
                ", placeId=" + placeId +
                ", preferedGenderId=" + preferedGenderId +
                //", timeSlots=" + timeSlots +
                ", climbLevels=" + climbLevels +
                ", isActive=" + isActive +
                '}';
    }
}
