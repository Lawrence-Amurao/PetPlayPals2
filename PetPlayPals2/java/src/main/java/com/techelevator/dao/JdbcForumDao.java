package com.techelevator.dao;

import com.techelevator.model.Forum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcForumDao implements ForumDao{

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcForumDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Forum> getAllForums() {
        List<Forum> forums = new ArrayList<>();
        String sql = "SELECT id, forum_name, forum_description FROM forum";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            forums.add(mapRowToForum(results));
        }
        return forums;
    }

    @Override
    public Forum getForumById(int forumId) {
        Forum forum = new Forum();
        String sql = "SELECT id, forum_name, forum_description FROM forum WHERE id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, forumId);
        if (result.next()) {
            forum = mapRowToForum(result);
        }
        return forum;
    }

    private Forum mapRowToForum(SqlRowSet rowSet) {
        Forum forum = new Forum();

        forum.setId(rowSet.getInt("id"));
        forum.setForumName(rowSet.getString("forum_name"));
        forum.setForumDescription(rowSet.getString("forum_description"));

        return forum;
    }
}
