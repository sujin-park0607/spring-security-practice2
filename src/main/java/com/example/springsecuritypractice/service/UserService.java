package com.example.springsecuritypractice.service;

import com.example.springsecuritypractice.domain.User;
import com.example.springsecuritypractice.domain.dto.UserDto;
import com.example.springsecuritypractice.domain.dto.UserJoinRequest;
import com.example.springsecuritypractice.repository.UserRepository;
import com.example.springsecuritypractice.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.token.secret}")
    private String key;

    private long expirTimeMs = 1000* 60 * 60;

    public String login(String userName, String password) {
        //id가 있는지 확인
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("userName이 잘못됐습니다."));
        log.info("1");

        //password가 일치하는지 확인
        if(!encoder.matches(password, user.getPassword())){
            log.info("password error");
            throw new RuntimeException("password가 잘못됐습니다");
        }

        return JwtTokenUtil.createToken(userName, key, expirTimeMs);
    }

    public User getUserByName(String userName){
        return userRepository.findByUserName(userName)
                .orElseThrow(()-> new RuntimeException(""));
    }

    public UserDto join(UserJoinRequest userJoinRequest) {
        //같은 아이디는 없는지
        userRepository.findByUserName(userJoinRequest.getUserName())
                .ifPresent(user -> {
                    throw new RuntimeException("중복된 아이디");
                });
        User savedUser = userRepository.save(userJoinRequest.toEntity(encoder.encode(userJoinRequest.getPassword())));

        return UserDto.builder()
                .id(savedUser.getId())
                .userName(savedUser.getUserName())
                .password(savedUser.getPassword())
                .email(savedUser.getEmail())
                .build();
    }
}
