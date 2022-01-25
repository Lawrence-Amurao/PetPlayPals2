package com.techelevator.dao;

import com.techelevator.model.User;

import java.util.List;
import java.util.Map;

public interface UserDao {

    List<User> findAll();

    User getUserById(Long userId);

    User findByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password, String role);

//    Map<Integer, Integer> getUserAvailability(int userId);
//
//    Map<String, String> getAvailabilityMap();
}
