package com.hk.transportProject.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", updatable = false)
    private String userId;

    @Column(name = "userEmail", nullable = false, unique = true)
    private String userEmail;

    @Column(name = "userPwd")
    private String userPwd;

    @Builder
    public User(String userEmail, String userPwd, String auth) {
        this.userEmail = userEmail;
        this.userPwd = userPwd;
    }

    public User(String userId, String userEmail, String userPwd, String auth) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userPwd = userPwd;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getPassword() {
        return userPwd;
    }

    @Override
    public String getUsername() {
        return userEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
