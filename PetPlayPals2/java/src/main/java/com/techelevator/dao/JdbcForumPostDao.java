package com.techelevator.dao;

import com.techelevator.model.ForumPost;
import com.techelevator.model.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


@Component
public class JdbcForumPostDao implements ForumPostDao{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcForumPostDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }



    @Override
    public ForumPost newPost(ForumPost post) {
        String sql = "INSERT INTO forum_message (topic_id, user_id, message_text) " +
                "VALUES (?, ?, ?) returning id";
        Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, post.getTopicId(), post.getUserId(), post.getMessageContent());
        return getMessageById(newId);
    }

    @Override
    public ForumPost updatePost(int postId, ForumPost post) {
        String sql = "UPDATE forum_message SET message_text = ? WHERE id = ?";
        jdbcTemplate.update(sql, post.getMessageContent(), postId);
        return null;

    }

    @Override
    public void deletePost(int postId) {
        String sql = "DELETE FROM forum_message WHERE id = ?";
        jdbcTemplate.update(sql, postId);

    }

    @Override
    public List<ForumPost> getAllPostsByTopic(int topicId) {
        List<ForumPost> allPosts = new ArrayList<>();
        String sql = "SELECT f.id, f.topic_id, f.user_id, f.time_stamp, f.message_text, u.username, p.pet_name " +
                "FROM forum_message f JOIN users u ON f.user_id = u.user_id " +
                "JOIN pet p ON u.user_id = p.owner_id  WHERE f.topic_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, topicId);
        while(results.next()) {
            ForumPost post = mapRowToForumPost(results);
            post.setUsername(results.getString("username"));
            post.setPetName(results.getString("pet_name"));
            allPosts.add(post);
        }

        return allPosts;
    }

    public ForumPost getMessageById(int postId) {
        ForumPost post = new ForumPost();
        String sql = "SELECT id, topic_id, user_id, time_stamp, message_text " +
                "FROM forum_message WHERE id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, postId);
        if (results.next()) {
            post = mapRowToForumPost(results);
        }
        return post;
    }

    @Override
    public List<ForumPost> getPostsByUserId(int userId) {
        List<ForumPost> postsByUserId = new ArrayList<>();
        String sql = "SELECT f.id, f.topic_id, f.user_id, f.time_stamp, f.message_text, u.username, p.pet_name " +
                "FROM forum_message f JOIN users u ON f.user_id = u.user_id " +
                "JOIN pet p ON u.user_id = owner_id " +
                "WHERE f.user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()) {
            ForumPost post = mapRowToForumPost(results);
            post.setUsername(results.getString("username"));
            post.setPetName(results.getString("pet_name"));
            postsByUserId.add(post);
        }

        return postsByUserId;
    }


    private ForumPost mapRowToForumPost(SqlRowSet result) {
        ForumPost post = new ForumPost();
        post.setPostId(result.getInt("id"));
        post.setTopicId(result.getInt("topic_id"));
        post.setUserId(result.getInt("user_id"));
        post.setPostDate(result.getTimestamp("time_stamp"));
        post.setMessageContent(result.getString("message_text"));
        return post;
    }
}
