package com.example.springsecuritypractice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String userName;
    private String password;
    private String email;
}
