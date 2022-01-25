package com.techelevator.dao;

import com.techelevator.model.Topic;

import java.util.List;

public interface TopicDao {

    List<Topic> getAllTopics();
    List<Topic> getTopicsByUserId(int userId);
    List<Topic> getTopicsByForumId(int forumId);

    Topic createNewTopic(Topic topic);
    Topic getTopicById(int topicId);
    Topic getTopicByName(String topicName);
    void deleteTopic(int topicID);


}
