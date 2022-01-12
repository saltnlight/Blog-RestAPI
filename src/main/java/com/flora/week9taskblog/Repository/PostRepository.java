package com.flora.week9taskblog.Repository;

import com.flora.week9taskblog.Payload.Request.PostRequest;
import com.flora.week9taskblog.Payload.Response.AddRemoveObjResponse;
import com.flora.week9taskblog.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class PostRepository extends JdbcDaoSupport {

    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);
    }

    public int savePost(Long userId, PostRequest postRequest) {
        String sql = "INSERT INTO posts (title, body, user_id) VALUES (?, ?, ?)";

        int result = getJdbcTemplate().update(sql, new Object[]{
                postRequest.getTitle(),postRequest.getBody(),userId
        });

        return result;
    }

    public List<Post> getUsersPosts(Long userId) {
        assert getJdbcTemplate() != null;
        return getJdbcTemplate().query(
                "SELECT * FROM posts WHERE user_id = ?",
                BeanPropertyRowMapper.newInstance(Post.class),
                new Object[] {userId});
    }

    public List<Post> getAllPosts() {
        var sql = "SELECT * FROM posts";
        assert getJdbcTemplate() != null;
        return getJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(Post.class));
    }

    public List<Post> searchPosts(String search) {
        var sql = "SELECT * FROM posts WHERE title @@ to_tsquery(?)";
        Object[] param = new Object[] {search};
        assert getJdbcTemplate() != null;
        return getJdbcTemplate().query(sql,
                BeanPropertyRowMapper.newInstance(Post.class),
                param);
    }

    public boolean favorite(Long userId, Long postId) {
        // Check if param pair exist
        var sql = "SELECT * FROM favouriteposts WHERE user_id = ? AND post_id = ?";
        Object[] param = new Object[]{userId, postId};
        assert getJdbcTemplate() != null;
        List<AddRemoveObjResponse> response = getJdbcTemplate().query(sql,
                BeanPropertyRowMapper.newInstance(AddRemoveObjResponse.class),
                param);

        // if not, add to DB i.e like
        if (response.isEmpty() || response == null){
            String sqlInsert = "INSERT INTO  favouriteposts" + "(user_id, post_id) " +
                            "VALUES (?, ?)" ;

            getJdbcTemplate().update(sqlInsert, new Object[]{userId, postId});
            return true;
        }

        // else, unlike
        getJdbcTemplate().update("DELETE from favouriteposts WHERE user_id = ? AND post_id = ?",
                        new Object[] { userId, postId });
        return false;
    }

    public boolean likeUnlike(Long userId, Long postId) {
        String sql = "SELECT * FROM postlikes WHERE user_id = ? AND post_id = ?";
        List<AddRemoveObjResponse> response = getJdbcTemplate().query(
                sql,
                BeanPropertyRowMapper.newInstance(AddRemoveObjResponse.class),
                new Object[]{userId, postId});

        if (response.isEmpty() || response == null){
            getJdbcTemplate().update("INSERT INTO  postlikes" + "(user_id, post_id) " +
                                        "VALUES (?, ?)",
                                    new Object[]{userId, postId});
            return true;
        }

        getJdbcTemplate().update("DELETE from postlikes WHERE user_id = ? AND post_id = ?",
                                new Object[] { userId, postId });
        return false;
    }
}
