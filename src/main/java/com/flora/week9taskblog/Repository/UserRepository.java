package com.flora.week9taskblog.Repository;

import com.flora.week9taskblog.Payload.Response.ConnectionResponse;
import com.flora.week9taskblog.Payload.Response.UserResponse;
import com.flora.week9taskblog.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final PageRequest pageable = PageRequest.of(0, 5);

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int saveUser(User user) {
        String sql = "UPDATE users " +
                "SET username=?, firstName=?, lastName=?, email=?, age=?, password=?, enabled=?, deactivated_at=?" +
                "WHERE id = ?" ;

        int result = jdbcTemplate.update(sql, user.getUsername(),user.getFirstName(), user.getLastName(),
                user.getEmail(), user.getAge(), user.getPassword(), user.getEnabled(), user.getDeactivated_at(),
                user.getId());

        return result;
    }

    public int deleteUserById(Long userId){
        return jdbcTemplate.update("DELETE from users WHERE id = ?", userId);
    }

    public UserResponse addConnect(Long userId, Long connectId) {
        jdbcTemplate.update(
                "INSERT INTO connections (user_id, connection_id) VALUES (?, ?)",
                userId, connectId);
        return new UserResponse("Connection created", getUserById(connectId));
    }

    public Page<User> getAllConnections(List<Long> connectIds, Pageable pageable){
        List<User> users = connectIds.stream().map(id -> {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM users WHERE id = ?",
                    BeanPropertyRowMapper.newInstance(User.class),
                    id);
        }).collect(Collectors.toList());

        return new PageImpl<>(users, pageable,3L);
    }

    public User getUserById(Long id){
        return jdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE id = ?",
                BeanPropertyRowMapper.newInstance(User.class),
                id);
    }

    public List<User> getAllUsers() {
        var sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(User.class));
    }

    public List<Long> getUserConnectionsIds(Long userId){
        List<ConnectionResponse> list = jdbcTemplate.query(
                "SELECT * FROM connections WHERE connections.user_id = ?",
                BeanPropertyRowMapper.newInstance(ConnectionResponse.class),
                userId);
        return list.stream().map(i -> {
            return i.getConnection_id();
        }).collect(Collectors.toList());
    }

    public Long lookUpUserIdFromUsername(String username){
        Integer rows = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM users",Integer.class,
                new Object[]{}
        );
        if (rows == 0) return 0L;

        List<User> users = jdbcTemplate.query(
                "SELECT * FROM users WHERE username = ?",
                BeanPropertyRowMapper.newInstance(User.class),
                username);
        if (users == null || users.isEmpty()) return 0L;
//        System.out.println(users.get(0));
        return users.get(0).getId();
    }

    public boolean connectionExists(Long userId, Long conId){
        Integer rows = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM connections",Integer.class,
                new Object[]{}
        );
        if (rows == 0) return false;

        List<ConnectionResponse> response = jdbcTemplate.query(
                "SELECT * FROM connections WHERE user_id = ? AND connection_id = ?",
                BeanPropertyRowMapper.newInstance(ConnectionResponse.class),
                userId, conId);
        return !response.isEmpty();
    }
}
