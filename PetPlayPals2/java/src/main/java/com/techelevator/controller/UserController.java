package com.techelevator.controller;

import com.techelevator.dao.ForumPostDao;
import com.techelevator.dao.UserDao;
import com.techelevator.exception.PlayDateNotFoundException;
import com.techelevator.exception.UserNotFoundException;
import com.techelevator.model.ForumPost;
import com.techelevator.model.PlayDate;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(path = "/users/{id}", method = RequestMethod.GET)
    public User getUserById (@PathVariable("id") Long userId) throws UserNotFoundException {
        return userDao.getUserById(userId);
    }
}
