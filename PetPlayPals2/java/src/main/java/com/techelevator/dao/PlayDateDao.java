package com.techelevator.dao;


import com.techelevator.model.PlayDate;

import java.util.List;

public interface PlayDateDao {

    List<PlayDate> getAllPlayDates();
    List<PlayDate> getPlayDatesById(int userId);

    List<PlayDate> getUserPlayDates(int userId);

    PlayDate createNewPlayDate(PlayDate playDate);
    PlayDate updatePlayDate(PlayDate playDate, int playDateId);
    void deletePlayDate(int playDateId);
    PlayDate getPlayDate (int playDateId);

    // Begin Attendee Methods


}
