package com.techelevator.controller;

import com.techelevator.dao.ForumDao;
import com.techelevator.dao.ForumPostDao;
import com.techelevator.dao.TopicDao;
import com.techelevator.exception.ForumNotFoundException;
import com.techelevator.exception.TopicNotFoundException;
import com.techelevator.model.Forum;
import com.techelevator.model.ForumPost;
import com.techelevator.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class ForumController {

    @Autowired
    private ForumDao forumDao;

    @Autowired
    private TopicDao topicDao;

    @RequestMapping(path = "/forums", method = RequestMethod.GET)
    public List<Forum> getAllForums() {
        return forumDao.getAllForums();
    }

    @RequestMapping(path = "/forums/{id}", method = RequestMethod.GET)
    public Forum getForumById(@PathVariable("id") int forumId)
        throws ForumNotFoundException {
        return forumDao.getForumById(forumId);
    }

    // Begin Topic Methods
    @RequestMapping(path = "/topics", method = RequestMethod.GET)
    public List<Topic> getAllTopics() {
        return topicDao.getAllTopics();
    }

    @RequestMapping (path = "/topics/users/{id}", method = RequestMethod.GET)
    public List<Topic> getTopicsByUserId(@PathVariable("id") int userId) {
        return topicDao.getTopicsByUserId(userId);
    }

    @RequestMapping (path = "/topics/forums/{id}", method = RequestMethod.GET)
    public List<Topic> getTopicsByForumId(@PathVariable("id") int forumId) {
        return topicDao.getTopicsByForumId(forumId);
    }

    @RequestMapping (path = "/topics/{id}", method = RequestMethod.GET)
    public Topic getTopicById(@PathVariable("id") int topicId)
            throws TopicNotFoundException {
        return topicDao.getTopicById(topicId);
    }

    @RequestMapping (path = "/topics/{name}", method = RequestMethod.GET)
    public Topic getTopicByName(@PathVariable("name") String topicName)
            throws TopicNotFoundException {
        return topicDao.getTopicByName(topicName);
    }

    @RequestMapping (path = "/topics", method = RequestMethod.POST)
    public Topic createNewTopic(@RequestBody Topic topic)
            throws TopicNotFoundException {
        return topicDao.createNewTopic(topic);
    }

    @RequestMapping (path = "/topics/{id}", method = RequestMethod.DELETE)
    public void removeTopic(@PathVariable("id") int topicId)
            throws TopicNotFoundException {
        topicDao.deleteTopic(topicId);
    }


}
