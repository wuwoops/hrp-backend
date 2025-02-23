package com.dave.hrpbackend.security;

import com.dave.hrpbackend.entity.Authority;
import com.dave.hrpbackend.entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Data
public class CustomUserDetails implements UserDetails {
    private String id;
    private String username;
    private String password;
    private String nickname;
    private List<Authority> Authorities;

    public CustomUserDetails() {
    }

    public CustomUserDetails(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.Authorities = Arrays.stream(user.getAuthorities().split(",")).map(Authority::valueOf).toList();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.Authorities
                .stream()
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
