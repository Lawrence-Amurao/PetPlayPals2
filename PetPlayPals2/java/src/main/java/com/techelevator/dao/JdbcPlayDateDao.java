package com.techelevator.dao;


import com.techelevator.model.Pet;
import com.techelevator.model.PlayDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcPlayDateDao implements PlayDateDao{

    @Autowired
    private PetDao petDao;


    private JdbcTemplate jdbcTemplate;
    //PLAYDATE ATTENDEES PULLED IN

    @Autowired
    public JdbcPlayDateDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public PlayDate getPlayDate(int playDateId) {
        PlayDate playdate = new PlayDate();
        String sql = "SELECT id, owner_id, street_address, location_name, location_type, playdate_description, activity, event_date, start_time, duration_in_minutes, max_attendees " +
                "FROM playdate WHERE id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, playDateId);
        if (results.next()) {
            playdate = mapRowToPlaydate(results);
        }
        return playdate;
    }

    public List<PlayDate> getAllPlayDates() {
        List<PlayDate> playdates = new ArrayList<>();
        String sql = "SELECT id, owner_id, street_address, location_name, location_type, playdate_description, activity, event_date, start_time, duration_in_minutes, max_attendees " +
                "FROM playdate";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            playdates.add(mapRowToPlaydate(results));
        }
        return playdates;
    }

    @Override
    public List<PlayDate> getPlayDatesById(int userId) {
        List<PlayDate> playdates = new ArrayList<>();
        String sql = "SELECT id, owner_id, street_address, location_name, location_type, activity, playdate_description, event_date, start_time, duration_in_minutes, max_attendees " +
            "FROM playdate WHERE owner_id = ? ORDER BY event_date";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()) {
            playdates.add(mapRowToPlaydate(results));
        }
        return playdates;
    }

    @Override
    public List<PlayDate> getUserPlayDates(int userId) {
        List<PlayDate> userPlaydates = new ArrayList<>();
        String sql = "SELECT DISTINCT playdate_id, playdate.event_date FROM playdate_attendee " +
                "JOIN playdate ON playdate.id = playdate_attendee.playdate_id " +
                "WHERE request_status NOT IN ('Rejected', 'Cancelled') AND pet_id IN (SELECT id FROM pet WHERE owner_id = ?) " +
                "ORDER BY playdate.event_date LIMIT 3;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while(results.next()) {
            PlayDate playdate = getPlayDate(results.getInt("playdate_id"));
            userPlaydates.add(playdate);
        }
        return userPlaydates;
    }

    @Override
    public PlayDate createNewPlayDate(PlayDate playDate) {
        // TODO - Creating a new playdate should also add the pet in question as an attendee of that playdate, with the status "Host"
        String sql = "INSERT INTO playdate (owner_id, street_address, location_name, location_type, activity, playdate_description, event_date, start_time, duration_in_minutes, max_attendees) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?) RETURNING id;";
        Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, playDate.getOwnerId(), playDate.getStreetAddress(), playDate.getLocationName(), playDate.getLocationType(),
                playDate.getActivity(), playDate.getDescription(), playDate.getDate(), playDate.getStartTime(), playDate.getDurationInMinutes(), playDate.getMaxAttendees());
        sql = "INSERT INTO playdate_attendee (pet_id, playdate_id, request_status) " +
                "VALUES (?, ?, 'Main');";
        jdbcTemplate.update(sql, playDate.getHostPet().getPetId(), newId);
        return getPlayDate(newId);
    }

    @Override
    public PlayDate updatePlayDate(PlayDate playDate, int playDateId) {
        String sql = "UPDATE playdate " +
                "SET owner_id = ?, street_address = ?, location_name = ?, location_type = ?, activity = ?, playdate_description = ?, event_date = ?, start_time = ?, duration_in_minutes = ?, max_attendees = ? " +
                "WHERE id = ?;";
        jdbcTemplate.update(sql, playDate.getOwnerId(), playDate.getStreetAddress(), playDate.getLocationName(), playDate.getLocationType(), playDate.getDescription(),
                playDate.getActivity(), playDate.getDate(), playDate.getStartTime(), playDate.getDurationInMinutes(), playDate.getMaxAttendees(), playDateId);

        return getPlayDate(playDateId);
    }

    @Override
    public void deletePlayDate(int playDateId) {
        String sql = "DELETE FROM playdate_attendee WHERE playdate_id = ?;";
        jdbcTemplate.update(sql, playDateId);
        sql = "DELETE FROM playdate WHERE id = ?;";
        jdbcTemplate.update(sql, playDateId);
    }

    // Begin Attendee Methods

    private PlayDate mapRowToPlaydate(SqlRowSet rowSet) {
        PlayDate playdate = new PlayDate();

        playdate.setPlayDateId(rowSet.getInt("id"));
        playdate.setOwnerId(rowSet.getInt("owner_id"));

        playdate.setStreetAddress(rowSet.getString("street_address"));
        playdate.setLocationName(rowSet.getString("location_name"));
        playdate.setLocationType(rowSet.getString("location_type"));

        playdate.setHostPet(petDao.getHostPet(playdate.getPlayDateId()));

        playdate.setActivity(rowSet.getString("activity"));
        playdate.setDescription(rowSet.getString("playdate_description"));
        playdate.setMaxAttendees(rowSet.getInt("max_attendees"));

        if (rowSet.getDate("event_date") != null) {
            playdate.setDate(rowSet.getDate("event_date").toLocalDate());
        }
        if (rowSet.getString("start_time") != null) {
            playdate.setStartTime(rowSet.getTime("start_time").toLocalTime());
        }
        playdate.setDurationInMinutes(rowSet.getInt("duration_in_minutes"));

        return playdate;
    }
}
