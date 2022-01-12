package com.flora.week9taskblog.Repository;

import com.flora.week9taskblog.Payload.Request.CommentRequest;
import com.flora.week9taskblog.Payload.Response.AddRemoveObjResponse;
import com.flora.week9taskblog.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepository{
    private JdbcTemplate jdbcTemplate;

    public CommentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int saveComment(CommentRequest commentRequest) {
        String sql = "INSERT INTO comments " + "(body, user_id, post_id) " + "VALUES (?, ?, ?)" ;

        int result = jdbcTemplate.update(sql, new Object[]{
                commentRequest.getBody(),commentRequest.getUserId(),commentRequest.getPostId()
        });

        return result;
    }

    public Page<Comment> searchComment(String search, Pageable pageable) {
        var sql = "SELECT * FROM comments WHERE comments.body @@ to_tsquery(?)";
        List<Comment> comments = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Comment.class),
                new Object[] {search});
        return new PageImpl<>(comments, pageable,3L);
    }

    public Page<Comment> getPostComments(Long postId, Pageable pageable) {
        var sql = "SELECT * FROM comments WHERE post_id = ?";
        Object[] param = new Object[] {postId};
        List<Comment> comments = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Comment.class),
                param);
        return new PageImpl<>(comments, pageable,3L);
    }

    public boolean likeUnlike(Long userId, Long commentId) {
        List<AddRemoveObjResponse> response = jdbcTemplate.query(
                "SELECT * FROM commentlikes WHERE user_id = ? AND comment_id = ?",
                BeanPropertyRowMapper.newInstance(AddRemoveObjResponse.class),
                new Object[]{userId, commentId});

        if (response.isEmpty() || response == null){
            jdbcTemplate.update("INSERT INTO  commentlikes" + "(user_id, comment_id) " +
                            "VALUES (?, ?)",
                    new Object[]{userId, commentId});
            return true;
        }

        jdbcTemplate.update("DELETE from commentlikes WHERE user_id = ? AND comment_id = ?",
                new Object[] { userId, commentId });
        return false;
    }
}
