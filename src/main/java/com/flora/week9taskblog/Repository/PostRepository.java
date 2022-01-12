package com.flora.week9taskblog.Repository;

import com.flora.week9taskblog.Payload.Request.PostRequest;
import com.flora.week9taskblog.Payload.Response.AddRemoveObjResponse;
import com.flora.week9taskblog.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRepository{
    private JdbcTemplate jdbcTemplate;

    public PostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int savePost(Long userId, PostRequest postRequest) {
        String sql = "INSERT INTO posts (title, body, user_id) VALUES (?, ?, ?)";

        int result = jdbcTemplate.update(sql, new Object[]{
                postRequest.getTitle(),postRequest.getBody(),userId
        });

        return result;
    }

    public Page<Post> getUsersPosts(Long userId, Pageable pageable) {
        List<Post> posts = jdbcTemplate.query(
                "SELECT * FROM posts WHERE user_id = ?",
                BeanPropertyRowMapper.newInstance(Post.class),
                new Object[] {userId});
        return new PageImpl<>(posts, pageable,3L);
    }

    public Page<Post> getAllPosts(Pageable pageable) {
        var sql = "SELECT * FROM posts";
        List<Post> posts = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Post.class));
        return new PageImpl<>(posts, pageable,3L);
    }

    public Page<Post> searchPosts(String search, Pageable pageable) {
        var sql = "SELECT * FROM posts WHERE title @@ to_tsquery(?)";
        Object[] param = new Object[] {search};
        List<Post> posts = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Post.class),
                param);
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

            jdbcTemplate.update(sqlInsert, new Object[]{userId, postId});
            return true;
        }

        // else, unlike
        jdbcTemplate.update("DELETE from favouriteposts WHERE user_id = ? AND post_id = ?",
                        new Object[] { userId, postId });
        return false;
    }

    public boolean likeUnlike(Long userId, Long postId) {
        String sql = "SELECT * FROM postlikes WHERE user_id = ? AND post_id = ?";
        List<AddRemoveObjResponse> response = jdbcTemplate.query(
                sql,
                BeanPropertyRowMapper.newInstance(AddRemoveObjResponse.class),
                new Object[]{userId, postId});

        if (response.isEmpty() || response == null){
            jdbcTemplate.update("INSERT INTO  postlikes" + "(user_id, post_id) " +
                                        "VALUES (?, ?)",
                                    new Object[]{userId, postId});
            return true;
        }

        jdbcTemplate.update("DELETE from postlikes WHERE user_id = ? AND post_id = ?",
                                new Object[] { userId, postId });
        return false;
    }
}
