package com.example.springsecuritypractice.service;

import com.example.springsecuritypractice.domain.User;
import com.example.springsecuritypractice.repository.UserRepository;
import com.example.springsecuritypractice.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("{jwt.token.secret}")
    private String key;

    private long expirTimeMs = 1000* 60 * 60;

    public String login(String userName, String password) {
        //id가 있는지 확인
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("userName이 잘못됐습니다."));

        //password가 일치하는지 확인
        if(!encoder.matches(password, user.getPassword())){
            throw new RuntimeException("password가 잘못됐습니다");
        }

        return JwtTokenUtil.createToken(userName, key, expirTimeMs);
    }
}
