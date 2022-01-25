package com.techelevator.model;

import org.springframework.stereotype.Component;

public class PlaydateAttendee {
    private Pet attendingPet;
    private int rating;
    private int playdateId;
    private String requestStatus;

    public Pet getAttendingPet() {
        return attendingPet;
    }

    public void setAttendingPet(Pet attendingPet) {
        this.attendingPet = attendingPet;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getPlaydateId() {
        return playdateId;
    }
    public void setPlaydateId(int playdateId) {
        this.playdateId = playdateId;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }
    // HELLO WHY ARE YOU NOT WORKING?
}
