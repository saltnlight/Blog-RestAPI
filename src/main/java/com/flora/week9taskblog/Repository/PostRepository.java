package com.flora.week9taskblog.Repository;

import com.flora.week9taskblog.Payload.Request.PostRequest;
import com.flora.week9taskblog.Payload.Response.AddRemoveObjResponse;
import com.flora.week9taskblog.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRepository{
    private final JdbcTemplate jdbcTemplate;

    RowMapper<Post> rowMapper = (rs, rowNum) -> {
        Post post = new Post();
        post.setId(rs.getLong("id"));
        post.setUserId(rs.getLong("user_id"));
        post.setTitle(rs.getString("title"));
        post.setBody(rs.getString("body"));
        return post;
    };

    public PostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int savePost(Long userId, PostRequest postRequest) {
        String sql = "INSERT INTO posts (title, body, user_id) VALUES (?, ?, ?)";

        int result = jdbcTemplate.update(sql, postRequest.getTitle(),postRequest.getBody(),userId);

        return result;
    }

    public Page<Post> getUsersPosts(Long userId, Pageable pageable) {
        List<Post> posts = jdbcTemplate.query(
                "SELECT * FROM posts WHERE user_id = ?",rowMapper, userId);
        return new PageImpl<>(posts, pageable,3L);
    }

    public Page<Post> getAllPosts(Pageable pageable) {
        var sql = "SELECT * FROM posts";
        List<Post> posts = jdbcTemplate.query(sql, rowMapper);
        return new PageImpl<>(posts, pageable,3L);
    }

    public Page<Post> searchPosts(String search, Pageable pageable) {
        var sql = "SELECT * FROM posts WHERE title @@ to_tsquery(?)";
        Object[] param = new Object[] {search};
        List<Post> posts = jdbcTemplate.query(sql, rowMapper, param);
        return new PageImpl<>(posts, pageable, 3L);
    }

    public boolean favorite(Long userId, Long postId) {
        // Check if param pair exist
        var sql = "SELECT * FROM favouriteposts WHERE user_id = ? AND post_id = ?";
        Object[] param = new Object[]{userId, postId};
        List<AddRemoveObjResponse> response = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(AddRemoveObjResponse.class),
                param);

        // if not, add to DB i.e like
        if (response.isEmpty() || response == null){
            String sqlInsert = "INSERT INTO  favouriteposts" + "(user_id, post_id) " +
                            "VALUES (?, ?)" ;

            jdbcTemplate.update(sqlInsert, userId, postId);
            return true;
        }

        // else, unlike
        jdbcTemplate.update("DELETE from favouriteposts WHERE user_id = ? AND post_id = ?",
                userId, postId);
        return false;
    }

    public boolean likeUnlike(Long userId, Long postId) {
        String sql = "SELECT * FROM postlikes WHERE user_id = ? AND post_id = ?";
        List<AddRemoveObjResponse> response = jdbcTemplate.query(
                sql,
                BeanPropertyRowMapper.newInstance(AddRemoveObjResponse.class),
                userId, postId);

        if (response.isEmpty() || response == null){
            jdbcTemplate.update("INSERT INTO  postlikes" + "(user_id, post_id) " +
                                        "VALUES (?, ?)",
                    userId, postId);
            return true;
        }

        jdbcTemplate.update("DELETE from postlikes WHERE user_id = ? AND post_id = ?",
                userId, postId);
        return false;
    }
}
