package com.example.springsecuritypractice.controller;

import com.example.springsecuritypractice.domain.dto.ReviewCreateRequest;
import com.example.springsecuritypractice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final UserService userService;

    @PostMapping
    public String write(@RequestBody ReviewCreateRequest reviewCreateRequest, Authentication authentication){
        log.info("authentication: {}",authentication);
        return "리뷰 등록 완료";
    }
}
