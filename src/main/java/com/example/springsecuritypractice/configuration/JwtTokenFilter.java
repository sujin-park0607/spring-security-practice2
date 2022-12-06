package com.example.springsecuritypractice.configuration;

import com.example.springsecuritypractice.service.UserService;
import com.example.springsecuritypractice.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final String secretKey;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION); //중간에 바뀌면 안되기 때문에 final로 선언, 인증을 위한 헤더
        log.info("authorizationHeader:{}",authorizationHeader);

        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
            log.error("헤더가 null이거나 잘못되었습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        final String token;
        //token 분리
        try{
            token = authorizationHeader.split(" ")[1];
        }catch (Exception e){
            log.error("token추출이 실패했습니다");
            filterChain.doFilter(request, response);
            return;
        }

        //token이 만료됐는지 check
        if(JwtTokenUtil.isExpired(token, secretKey)){
            filterChain.doFilter(request, response);
            return;
        }

        //userName 꺼내기
        String userName = JwtTokenUtil.getUserName(token, secretKey);
        log.info("userName:{}", userName);

        //권한 여부 결정
        //현재는 막아놓은 상태
        //문 열어주기
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("", null, List.of(new SimpleGrantedAuthority("USER")));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }

}
