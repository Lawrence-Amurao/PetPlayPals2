package com.techelevator.dao;

import com.techelevator.model.Forum;

import java.util.List;

public interface ForumDao {

    List<Forum> getAllForums();

    Forum getForumById(int forumId);
}
