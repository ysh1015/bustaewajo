package com.hk.transportProject.member.service;

import com.hk.transportProject.member.domain.User;
import com.hk.transportProject.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public User loadUserByUsername(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException((userId)));
    }
}