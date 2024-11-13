package com.hk.transportProject.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequest {
    private String userId;
    private String userEmail;
    private String userPwd;
}
