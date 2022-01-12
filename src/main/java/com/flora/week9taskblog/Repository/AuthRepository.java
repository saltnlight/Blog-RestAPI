package com.flora.week9taskblog.Repository;

import com.flora.week9taskblog.model.User;
import com.flora.week9taskblog.Payload.Request.LoginRequest;
import com.flora.week9taskblog.Payload.Request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Repository
public class AuthRepository extends JdbcDaoSupport {

    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);
    }

    public int registerUser(RegisterRequest registerRequest) {
        String sql = "INSERT INTO users " +
                "(username, firstName, lastName, email, age, password, deactivated) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)" ;

        int result = getJdbcTemplate().update(sql, new Object[]{
                registerRequest.getUsername(),registerRequest.getFirstName(),
                registerRequest.getLastName(), registerRequest.getEmail(),
                registerRequest.getAge(), registerRequest.getPassword(), registerRequest.getDeactivated()
        });

        return result;
    }

    public User loginUser(LoginRequest loginRequest) {
        var sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        Object[] param = new Object[] {loginRequest.getUsername(), loginRequest.getPassword()};

        assert getJdbcTemplate() != null;
        return getJdbcTemplate().queryForObject(sql,
                BeanPropertyRowMapper.newInstance(User.class),
                param);
    }
}
