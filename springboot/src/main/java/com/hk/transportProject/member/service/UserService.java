package com.hk.transportProject.member.service;

import com.hk.transportProject.member.domain.User;
import com.hk.transportProject.member.dto.AddUserRequest;
import com.hk.transportProject.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public String save(AddUserRequest dto) {
        return userRepository.save(User.builder()
                .userEmail(dto.getUserEmail())
                .userPwd(bCryptPasswordEncoder.encode(dto.getUserPwd()))
                .build()).getUserId();
    }
}
