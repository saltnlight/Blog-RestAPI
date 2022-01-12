package com.flora.week9taskblog.Repository;

import com.flora.week9taskblog.Payload.Request.PostRequest;
import com.flora.week9taskblog.Payload.Response.AddRemoveObjResponse;
import com.flora.week9taskblog.Payload.Response.ConnectionResponse;
import com.flora.week9taskblog.Payload.Response.PostResponse;
import com.flora.week9taskblog.Payload.Response.UserResponse;
import com.flora.week9taskblog.model.Post;
import com.flora.week9taskblog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PostRepository postRepository;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UserResponse addConnect(Long userId, String conUsername) {
        Long conId = lookUpUserIdFromUsername(conUsername);

        if (conId == 0) return new UserResponse("User doesn't exists", new ArrayList<>());

        if (!connectionExists(userId, conId)) {
            jdbcTemplate.update(
                    "INSERT INTO connections (user_id, connection_id) VALUES (?, ?)",
                    new Object[]{userId, conId}
            );
            return new UserResponse("Connection created", List.of(getUserById(conId)));
        }
        return new UserResponse("Connection already exists", List.of(getUserById(conId)));
    }

    public List<User> getAllConnections(Long userId){
        List<Long> listUserId = getUserConnectionsIds(userId);
        List<User> users = listUserId.stream().map(id -> {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM users WHERE id = ?",
                    BeanPropertyRowMapper.newInstance(User.class),
                    new Object[]{id}
            );
        }).collect(Collectors.toList());

        return users;
    }

    public PostResponse getOneConnectionPosts(Long userId, String conUsername){
        Long conId = lookUpUserIdFromUsername(conUsername);
        if (conId <= 0) return new PostResponse("User doesn't exists", new ArrayList<>());

        boolean connExists = connectionExists(userId, conId);
        if (conId >= 1 && connExists) return new PostResponse("Found", postRepository.getUsersPosts(conId));

        return new PostResponse("User isn't your connection", postRepository.getUsersPosts(conId));
    }

    public List<List<Post>> getAllConnectionsPosts(Long userId){
        List<Long> listUserId = getUserConnectionsIds(userId);
        List<List<Post>> connPosts = listUserId.stream().map(id -> {
            return postRepository.getUsersPosts(id);
        }).collect(Collectors.toList());

        return connPosts;
    }

    private User getUserById(Long id){
        return jdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE id = ?",
                BeanPropertyRowMapper.newInstance(User.class),
                new Object[] {id});
    }

    private List<Long> getUserConnectionsIds(Long userId){
        List<ConnectionResponse> list = jdbcTemplate.query(
                "SELECT * FROM connections WHERE connections.user_id = ?",
                BeanPropertyRowMapper.newInstance(ConnectionResponse.class),
                new Object[] {userId}
        );
        return list.stream().map(i -> {
            return i.getConnection_id();
        }).collect(Collectors.toList());
    }

    private Long lookUpUserIdFromUsername(String username){
        Integer rows = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM users",Integer.class,
                new Object[]{}
        );
        if (rows == 0) return 0L;

        List<User> users = jdbcTemplate.query(
                "SELECT * FROM users WHERE username = ?",
                BeanPropertyRowMapper.newInstance(User.class),
                new Object[] {username}
        );
        if (users == null || users.isEmpty()) return 0L;

        return users.get(0).getId();
    }

    private boolean connectionExists(Long userId, Long conId){
        Integer rows = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM connections",Integer.class,
                new Object[]{}
        );
        if (rows == 0) return false;

        List<ConnectionResponse> response = jdbcTemplate.query(
                "SELECT * FROM connections WHERE user_id = ? AND connection_id = ?",
                BeanPropertyRowMapper.newInstance(ConnectionResponse.class),
                new Object[]{userId, conId}
        );
        if (response.isEmpty()) return false;
        return true;
    }
}