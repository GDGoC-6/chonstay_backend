package com.webproject.chonstay_backend.user;

import static org.springframework.http.HttpStatus.CREATED;

import com.webproject.chonstay_backend.user.dto.SignUpRequest;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody SignUpRequest request) {
        userService.registerUser(request);
        return ResponseEntity
                .status(CREATED)
                .body("User successfully registered.");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestParam String userEmail, @RequestParam String password) {
        Optional<User> user = userService.login(userEmail, password);
        if (user.isPresent()) {
            User loggedInUser = user.get();
            return ResponseEntity.ok(Map.of(
                    "message", "로그인 성공. 환영합니다, " + loggedInUser.getName() + "님!",
                    "user_id", loggedInUser.getUserId()
            ));
        } else {
            return ResponseEntity.status(401).body(Map.of(
                    "message", "로그인에 실패했습니다. 이메일 또는 비밀번호를 확인하세요."
            ));
        }
    }

    // 마이페이지
    @GetMapping("/mypage")
    public String getUserNameById(Long userId) {
        return userService.getUserNameById(userId);
    }

    // 회원탈퇴
    @DeleteMapping
    public void DeleteUser(Long userId) {
        userService.deleteUserById(userId);
    }

    // 역할 변경 엔드포인트
    @PutMapping("/role")
    public ResponseEntity<String> updateUserRole(Long userId, Role role) {
        userService.updateUserRole(userId, role);
        return ResponseEntity.ok("User role updated successfully.");
    }
}
