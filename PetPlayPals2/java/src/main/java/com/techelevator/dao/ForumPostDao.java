package com.techelevator.dao;

import com.techelevator.model.ForumPost;
import com.techelevator.model.Pet;

import java.sql.Timestamp;
import java.util.List;

public interface ForumPostDao {

    public ForumPost newPost(ForumPost post);
    public ForumPost updatePost(int postId, ForumPost post);
    public void deletePost(int postId);
    public ForumPost getMessageById(int postId);

    List <ForumPost> getAllPostsByTopic(int topicId);
    List <ForumPost> getPostsByUserId(int userId);

}
