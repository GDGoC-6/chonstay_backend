package com.webproject.chonstay_backend.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("회원가입이 완료되었습니다!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("회원가입에 실패했습니다. 다시 시도해주세요.");
        }
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String userEmail, @RequestParam String password) {
        Optional<User> user = userService.login(userEmail, password);
        if (user.isPresent()) {
            return ResponseEntity.ok("로그인 성공. 환영합니다, !");
        } else {
            return ResponseEntity.status(401).body("로그인에 실패했습니다. 이메일 또는 비밀번호를 확인하세요.");
        }
    }
}
