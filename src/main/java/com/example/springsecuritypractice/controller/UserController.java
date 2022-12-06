package com.example.springsecuritypractice.controller;

import com.example.springsecuritypractice.domain.dto.UserDto;
import com.example.springsecuritypractice.domain.dto.UserJoinRequest;
import com.example.springsecuritypractice.domain.dto.UserJoinResponse;
import com.example.springsecuritypractice.domain.dto.UserLoginRequest;
import com.example.springsecuritypractice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<UserJoinResponse> join(@RequestBody UserJoinRequest userJoinRequest){
        UserDto userDto = userService.join(userJoinRequest);
        return ResponseEntity.ok().body(new UserJoinResponse(userDto.getUserName(), userDto.getEmail()));

    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserLoginRequest userLoginRequest){
        String token = userService.login(userLoginRequest.getUserName(), userLoginRequest.getPassword());
        return ResponseEntity.ok().body(token);
    }
}
