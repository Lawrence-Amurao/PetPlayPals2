package com.techelevator.dao;

import com.techelevator.model.Pet;
import com.techelevator.model.PlayDate;
import com.techelevator.model.PlaydateAttendee;

import java.util.List;

public interface PlaydateAttendeeDao {
    void updateAttendee(int playdateId, int petId, String updateStatus);
    void deleteAttendee(int playdateId, int petId);
    void addAttendee(int playdateId, int petId);

    List<PlaydateAttendee> getAllAttendees(int playdateId);

    List<PlaydateAttendee> getAttendeesByStatus(int playdateId, String requestStatus);

    List<PlaydateAttendee> getAllHostPets();
}
