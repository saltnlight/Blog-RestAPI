package com.flora.week9taskblog.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String phone;
    private String password;
    private Time created_at;
    private Boolean deactivated;
    private Time deactivated_at;
}
