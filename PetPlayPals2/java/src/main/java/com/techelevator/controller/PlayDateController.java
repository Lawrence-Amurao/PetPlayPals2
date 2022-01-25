package com.techelevator.controller;

import com.techelevator.dao.PlayDateDao;
import com.techelevator.dao.PlaydateAttendeeDao;
import com.techelevator.dao.UserDao;
import com.techelevator.exception.PlayDateNotFoundException;
import com.techelevator.model.Pet;
import com.techelevator.model.PlayDate;
import com.techelevator.model.PlaydateAttendee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class PlayDateController {

    @Autowired
    private PlayDateDao playDateDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PlaydateAttendeeDao playdateAttendeeDao;


    //create playDate start
    @RequestMapping(path = "/playdates/add", method = RequestMethod.POST)
    public PlayDate addNewPlayDate (@RequestBody PlayDate playDate)
        throws PlayDateNotFoundException {
        return playDateDao.createNewPlayDate(playDate);
    }

    //get playDate start
    @RequestMapping(path = "/playdates", method = RequestMethod.GET)
    public List<PlayDate> getAllPlayDates () {
        return playDateDao.getAllPlayDates();
    }

    @RequestMapping(path = "/playdates/{userId}", method = RequestMethod.GET)
    public List<PlayDate> getPlayDatesByUserId (@PathVariable("userId") int userId) {
        return playDateDao.getPlayDatesById(userId);
    }

    @RequestMapping(path = "/playdate/{id}", method = RequestMethod.GET)
    public PlayDate getPlayDate (@PathVariable("id") int playDateId) throws PlayDateNotFoundException {
        return playDateDao.getPlayDate(playDateId);
    }

    @RequestMapping(path = "/playdates/upcoming/{id}", method = RequestMethod.GET)
    public List<PlayDate> getUpcomingPlayDates (@PathVariable("id") int userId) {
        return playDateDao.getUserPlayDates(userId);
    }

    //update start
    @RequestMapping(path = "/playdates/update/{id}", method = RequestMethod.PUT)
    public PlayDate updatePlayDate (@RequestBody PlayDate playDate, @PathVariable("id") int playDateId)
            throws PlayDateNotFoundException {
        return playDateDao.updatePlayDate(playDate, playDateId);
    }

    //delete start (admin only or playdate owner)
    @RequestMapping(path = "/playdates/delete/{id}", method = RequestMethod.DELETE)
    public void deletePlayDate (@PathVariable("id") int playDateId) throws PlayDateNotFoundException {
        playDateDao.deletePlayDate(playDateId);
    }

    @RequestMapping(path = "/playdate/{id}/attendees", method = RequestMethod.GET)
    public List<PlaydateAttendee> getAllAttendees(@PathVariable("id") int playDateId) {
        return playdateAttendeeDao.getAllAttendees(playDateId);
    }

    @RequestMapping(path = "/playdate/{id}/{status}", method = RequestMethod.GET)
    public List<PlaydateAttendee> getAttendeesByStatus(@PathVariable("id") int playDateId, @PathVariable("status") String requestStatus) {
        return playdateAttendeeDao.getAttendeesByStatus(playDateId, requestStatus);
    }

    @RequestMapping(path = "/playdates/allHosts", method = RequestMethod.GET)
    public List<PlaydateAttendee> getAllHosts() {
        return playdateAttendeeDao.getAllHostPets();
    }

    @RequestMapping(path= "/playdate/{id}/request", method = RequestMethod.POST)
    public void playdateJoinRequest(@PathVariable("id") int playdateId, @RequestBody Pet pet){
        int petId = pet.getPetId();
        playdateAttendeeDao.addAttendee(playdateId, petId);
    }

    @RequestMapping(path = "/playdate/{id}/withdraw", method=RequestMethod.PUT)
    public void cancelRequest(@PathVariable("id") int playdateId, @RequestBody Pet pet) {
        int petId = pet.getPetId();
        playdateAttendeeDao.updateAttendee(playdateId, petId, "Cancelled");
    }

    @RequestMapping(path = "/playdate/{id}/approve", method=RequestMethod.PUT)
    public void approveRequest(@PathVariable("id") int playdateId, @RequestBody Pet pet) {
        int petId = pet.getPetId();
        playdateAttendeeDao.updateAttendee(playdateId, petId, "Approved");
    }

    @RequestMapping(path = "/playdate/{id}/reject", method=RequestMethod.PUT)
    public void rejectRequest(@PathVariable("id") int playdateId, @RequestBody Pet pet) {
        int petId = pet.getPetId();
        playdateAttendeeDao.updateAttendee(playdateId, petId, "Rejected");
    }

    @RequestMapping(path = "/playdate/{id}/resubmit", method=RequestMethod.PUT)
    public void resubmitRequest(@PathVariable("id") int playdateId, @RequestBody Pet pet) {
        int petId = pet.getPetId();
        playdateAttendeeDao.updateAttendee(playdateId, petId, "Pending");
    }

}
