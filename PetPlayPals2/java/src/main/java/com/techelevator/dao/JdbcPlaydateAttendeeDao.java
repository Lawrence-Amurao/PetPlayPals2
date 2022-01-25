package com.techelevator.dao;

import com.techelevator.model.Pet;
import com.techelevator.model.PlayDate;
import com.techelevator.model.PlaydateAttendee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcPlaydateAttendeeDao implements PlaydateAttendeeDao {

    @Autowired
    private PetDao petDao;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcPlaydateAttendeeDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void addAttendee(int playdateId, int petId) {
        String sql = "INSERT INTO playdate_attendee (pet_id, playdate_id, request_status) " +
                "VALUES (?, ?, 'Pending');";
        jdbcTemplate.update(sql, petId, playdateId);
    }

    @Override
    public void updateAttendee(int playdateId, int petId, String updateStatus) {
        String sql = "UPDATE playdate_attendee SET request_status = ? WHERE playdate_id = ? AND  pet_id = ? ;";
        jdbcTemplate.update(sql, updateStatus, playdateId, petId);
    }

    @Override
    public void deleteAttendee(int playdateId, int petId) {
        String sql = "DELETE FROM playdate_attendee WHERE playdate_id = ? AND pet_id = ?;";
        jdbcTemplate.update(sql, playdateId, petId);
    }

    @Override
    public List<PlaydateAttendee> getAllAttendees(int playdateId) {
        List<PlaydateAttendee> allAttendees = new ArrayList<>();
        String sql = "SELECT pet_id, request_status, playdate_id FROM playdate_attendee WHERE playdate_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, playdateId);
        while (results.next()) {
            PlaydateAttendee attendee = new PlaydateAttendee();
            Pet thisPet = petDao.getPetById(results.getInt("pet_id"));
            attendee.setAttendingPet(thisPet);
            attendee.setPlaydateId(results.getInt("playdate_id"));
            attendee.setRequestStatus(results.getString("request_status"));
            allAttendees.add(attendee);
        }
        return allAttendees;
    }

    @Override
    public List<PlaydateAttendee> getAttendeesByStatus(int playdateId, String requestStatus) {
        List<PlaydateAttendee> attendeesByStatus = new ArrayList<>();
        String sql = "SELECT pet_id, request_status, playdate_id FROM playdate_attendee WHERE playdate_id = ? AND request_status ILIKE ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, playdateId, requestStatus);
        while (results.next()) {
            PlaydateAttendee attendee = new PlaydateAttendee();
            Pet thisPet = petDao.getPetById(results.getInt("pet_id"));
            attendee.setAttendingPet(thisPet);
            attendee.setPlaydateId(results.getInt("playdate_id"));
            attendee.setRequestStatus(results.getString("request_status"));
            attendeesByStatus.add(attendee);
        }
        return attendeesByStatus;
    }

    @Override
    public List<PlaydateAttendee> getAllHostPets() {
        List<PlaydateAttendee> hostPets = new ArrayList<>();
        String sql = "SELECT DISTINCT ON(pet_id) pet_id, request_status, playdate_id FROM playdate_attendee WHERE request_status = 'Main';";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            PlaydateAttendee attendee = new PlaydateAttendee();
            Pet thisPet = petDao.getPetById(results.getInt("pet_id"));
            attendee.setAttendingPet(thisPet);
            attendee.setPlaydateId(results.getInt("playdate_id"));
            attendee.setRequestStatus(results.getString("request_status"));
            hostPets.add(attendee);
        }
        return hostPets;
    }
}
