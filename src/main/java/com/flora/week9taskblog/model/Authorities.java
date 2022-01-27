package com.flora.week9taskblog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Authorities {
    private  Long id;
    private String username;
    private String authority;

    public Collection<? extends GrantedAuthority> getAuthority() {
        return new ArrayList<>(Arrays.asList(new SimpleGrantedAuthority("USER")));
    }


}
