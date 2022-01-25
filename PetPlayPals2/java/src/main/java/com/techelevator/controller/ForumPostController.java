package com.techelevator.controller;

import com.techelevator.dao.ForumPostDao;
import com.techelevator.model.ForumPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class ForumPostController {

    @Autowired
    private ForumPostDao forumPostDao;

    @RequestMapping(path = "/messages", method = RequestMethod.POST)
    public ForumPost newMessage(@RequestBody ForumPost message) {
        return forumPostDao.newPost(message);
    }

    @RequestMapping(path = "/messages/{messageId}", method = RequestMethod.PUT)
    public ForumPost updateMessage(@RequestBody ForumPost message, @PathVariable int messageId) {
        ForumPost output = forumPostDao.updatePost(messageId, message);
        return output;
    }

    @RequestMapping(path = "/messages/{userId}", method = RequestMethod.GET)
    public List<ForumPost> getPostsByUser(@PathVariable int userId) {
        List<ForumPost> output = forumPostDao.getPostsByUserId(userId);
        return output;
    }

    @RequestMapping(path = "/messages/topics/{topicId}", method = RequestMethod.GET)
    public List<ForumPost> getPostsByTopic(@PathVariable int topicId) {
        List<ForumPost> output = forumPostDao.getAllPostsByTopic(topicId);
        return output;
    }
}
