package com.techelevator.dao;

import com.techelevator.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTopicDao implements TopicDao{

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTopicDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Topic> getAllTopics() {
        List<Topic> topics = new ArrayList<>();
        String sql = "Select id, topic_name, created, forum_id From topic";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            topics.add(mapRowToTopic(results));
        }
        return topics;
    }

    @Override
    public List<Topic> getTopicsByUserId(int userId) {
        List<Topic> topics = new ArrayList<>();
        String sql = "SELECT t.id, topic_name, created, forum_id From topic t " +
                "JOIN forum_message fm ON t.id = fm.topic_id " +
                "JOIN users u ON fm.user_id = u.user_id " +
                "WHERE u.user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()) {
            topics.add(mapRowToTopic(results));
        }
        return topics;
    }

    @Override
    public List<Topic> getTopicsByForumId(int forumId) {
        List<Topic> topics = new ArrayList<>();
        String sql = "SELECT id, topic_name, created, forum_id FROM topic WHERE forum_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, forumId);
        while (results.next()) {
            topics.add(mapRowToTopic(results));
        }
        return topics;
    }

    //had created before, but it's a time stamp so it'll auto create
    @Override
    public Topic createNewTopic(Topic topic) {
        String sql = "INSERT INTO topic (topic_name,forum_id) " +
                "VALUES (?,?) RETURNING id";
        Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, topic.getTopicName(), topic.getCreatedDate(), topic.getForumId());
        return getTopicById(newId);
    }

    @Override
    public Topic getTopicById(int topicId) {
        Topic topic = new Topic();
        String sql = "SELECT id, topic_name, created, forum_id FROM topic WHERE id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, topicId);
        if (result.next()) {
            topic = mapRowToTopic(result);
        }
        return topic;
    }

    @Override
    public Topic getTopicByName(String topicName) {
        Topic topic = new Topic();
        String sql = "SELECT id, topic_name, created, forum_id FROM topic WHERE topic_name LIKE ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, topicName);
        if (result.next()) {
            topic = mapRowToTopic(result);
        }
        return topic;
    }


    @Override
    public void deleteTopic(int topicID) {
        String sql = "DELETE FROM topic WHERE id = ?";
        jdbcTemplate.update(sql, topicID);

    }

    private Topic mapRowToTopic(SqlRowSet rowSet) {
        Topic topic = new Topic();

        topic.setId(rowSet.getInt("id"));
        topic.setForumId(rowSet.getInt("forum_id"));
        topic.setTopicName(rowSet.getString("topic_name"));
        topic.setCreatedDate(rowSet.getTimestamp("created").toLocalDateTime());

        return topic;
    }
}
