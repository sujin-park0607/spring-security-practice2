# 로그인 & 회원가입
JWT Token과 Spring Security를 사용하여 로그인 및 권한제어 코드 

---
### 프로젝트 
- userName, password를 넘겨주면 token을 받고 해당 유효시간까지 로그인 상태 유지 가능

### 실행방법
```aidl
git clone https://github.com/sujin-park0607/spring-security-practice2.git
Environment variable에 db와 secret key 수정
run
```

### EndPoint
- login: http://localhost:8081/api/v1/login
