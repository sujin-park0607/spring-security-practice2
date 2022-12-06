package com.example.springsecuritypractice.domain.dto;

import com.example.springsecuritypractice.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserJoinRequest {
    private String userName;
    private String password;
    private String email;

    public User toEntity(String password){
        return User.builder()
                .userName(this.userName)
                .password(password)
                .email(this.email)
                .build();

    }
}
