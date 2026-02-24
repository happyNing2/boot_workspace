package com.ex.basic.config.security;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

// userdetails 있는지 없는지
public class CustomUserDetails implements UserDetails {
    private long id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Long id, String username, String password, Collection<? extends  GrantedAuthority> authorities){
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public long getId(){
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
