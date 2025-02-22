package com.dave.hrpbackend.security;

import com.dave.hrpbackend.entity.Authority;
import com.dave.hrpbackend.utils.JwtTools;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String BEARER_PREFIX = "Bearer ";
    private static final List<String> EXCLUDED_PATHS = List.of("/login");
    @Autowired
    private JwtTools jwtTools;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return EXCLUDED_PATHS.contains(request.getServletPath());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null) {
            throw new UsernameNotFoundException("No Authorization header found");
        }

        String jwt = authHeader.substring(BEARER_PREFIX.length());
        Claims claims;
        try {
            claims = jwtTools.parseToken(jwt);
        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 建立 UserDetails 物件
        CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setId(claims.get("id", String.class));
        userDetails.setUsername(claims.get("username", String.class));
        userDetails.setNickname(claims.get("nickname", String.class));

        List<Authority> userAuthorities = ((List<Map>) claims.get("authorities"))
                .stream()
                .flatMap(map -> map.values().stream())
                .map(s -> Authority.valueOf((String) s))
                .toList();
        userDetails.setAuthorities(userAuthorities);

        // 放入 Security Context
        Authentication token = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(token);

        filterChain.doFilter(request, response);
    }

}