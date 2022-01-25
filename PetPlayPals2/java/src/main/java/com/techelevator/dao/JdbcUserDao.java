package com.techelevator.dao;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.techelevator.model.User;

@Service
public class JdbcUserDao implements UserDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int findIdByUsername(String username) {
        return jdbcTemplate.queryForObject("select user_id from users where username = ?", int.class, username);
    }

	@Override
	public User getUserById(Long userId) {
		String sql = "SELECT user_id, username, password_hash, role FROM users WHERE user_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
		if(results.next()) {
			return mapRowToUser(results);
		} else {
			throw new RuntimeException("userId "+userId+" was not found.");
		}
	}

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "select * from users";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            User user = mapRowToUser(results);
            users.add(user);
        }

        return users;
    }

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        for (User user : this.findAll()) {
            if( user.getUsername().toLowerCase().equals(username.toLowerCase())) {
                return user;
            }
        }
        throw new UsernameNotFoundException("User " + username + " was not found.");
    }

    @Override
    public boolean create(String username, String password, String role) {
        boolean userCreated = false;

        // create user
        String insertUser = "insert into users (username,password_hash,role) values(?,?,?)";
        String password_hash = new BCryptPasswordEncoder().encode(password);
        String ssRole = "ROLE_" + role.toUpperCase();

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        String id_column = "user_id";
        userCreated = jdbcTemplate.update(con -> {
                    PreparedStatement ps = con.prepareStatement(insertUser, new String[]{id_column});
                    ps.setString(1, username);
                    ps.setString(2, password_hash);
                    ps.setString(3, ssRole);
                    return ps;
                }
                , keyHolder) == 1;
        int newUserId = (int) keyHolder.getKeys().get(id_column);

        return userCreated;
    }

//    @Override
//    public Map<Integer, Integer> getUserAvailability(int userId) {
//        Map<Integer, Integer> availability = new HashMap<>();
//        String sql = "SELECT day_id, time_id FROM user_availability WHERE user_id ?;";
//        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
//
//        while (results.next()) {
//            int dayValue = results.getInt("day_id");
//            int timeValue = results.getInt("time_id");
//            availability.put(dayValue, timeValue);
//        }
//        return availability;
//    }
//
//    @Override
//    public Map<String, String> getAvailabilityMap() {
//        // This is what happens when you don't eat your wheaties, kids.
//        Map<String, String> availabilityMap = new HashMap<>();
////        List<String> days = new ArrayList<>();
////        List<String> times = new ArrayList<>();
////
////        String sql = "SELECT day FROM availability_day;";
////        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
////        while (results.next()) {
////            days.add(results.getString("day"));
////        }
////
////        sql = "SELECT time_block FROM availability_time;";
////        results = jdbcTemplate.queryForRowSet(sql);
////        while (results.next()) {
////            times.add(results.getString("time_block"));
////        }
////
////        for (String day : days ) {
////            for (String time : times) {
////                availabilityMap.put(day, time);
////            }
////        }
//
//        return availabilityMap;
//    }

    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getLong("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password_hash"));
        user.setAuthorities(rs.getString("role"));
        user.setActivated(true);
        return user;
    }
}
