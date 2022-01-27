package com.flora.week9taskblog.Security;

import com.flora.week9taskblog.Repository.AuthRepository;
import com.flora.week9taskblog.model.Authorities;
import com.flora.week9taskblog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private JwtUtil jwtUtil;
    private AuthRepository authRepository;
    private Authorities authorities;

    @Autowired
    public JwtFilter(JwtUtil jwtUtil, AuthRepository authRepository, Authorities authorities) {
        this.jwtUtil = jwtUtil;
        this.authRepository = authRepository;
        this.authorities = authorities;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestHeader = request.getHeader("Authorization");

        String jwt=null;
        String username = null;
        if(requestHeader != null && requestHeader.startsWith("Bearer ")){
            jwt = requestHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            User user = authRepository.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, user.getUsername())) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        user, null, authorities.getAuthority());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
