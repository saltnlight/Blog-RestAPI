package com.flora.week9taskblog.Repository;

import com.flora.week9taskblog.Payload.Request.CommentRequest;
import com.flora.week9taskblog.Payload.Response.AddRemoveObjResponse;
import com.flora.week9taskblog.model.Comment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepository{
    private JdbcTemplate jdbcTemplate;

//    @Autowired
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

    public List<Comment> searchComment(String search) {
        var sql = "SELECT * FROM comments WHERE comments.body @@ to_tsquery(?)";
        Object[] param = new Object[] {search};
        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Comment.class),
                param);
    }

    public List<Comment> getPostComments(Long postId) {
        var sql = "SELECT * FROM comments WHERE post_id = ?";
        Object[] param = new Object[] {postId};
        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Comment.class),
                param);
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
