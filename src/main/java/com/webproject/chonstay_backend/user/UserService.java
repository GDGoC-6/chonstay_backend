package com.webproject.chonstay_backend.user;

import com.webproject.chonstay_backend.user.dto.SignUpRequest;
import jakarta.persistence.EntityNotFoundException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserOrException(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException("User with ID " + userId + " not found"));
    }

    // 회원가입
    public void registerUser(SignUpRequest request) {
        try {
            User user = User.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .userPassword(request.getUserPassword())
                    .phoneNumber(request.getPhoneNumber())
                    .role(Role.GUEST)
                    .photo(null)
                    .build();
            user.setUserPassword(hashPassword(user.getUserPassword()));
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("회원가입 중 문제가 발생했습니다.", e);
        }
    }

    // 로그인
    public Optional<User> login(String userEmail, String rawPassword) {
        return userRepository.findByEmail(userEmail)
                .filter(user -> user.getUserPassword().equals(hashPassword(rawPassword)));
    }

    // Id로 유저 이름 가져오기
    public String getUserNameById(Long userId) {
        // 유저 데이터를 데이터베이스에서 조회
        return userRepository.findById(userId)
                .map(User::getName)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
    }

    // SHA-256 비밀번호 암호화
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("비밀번호 암호화 중 문제가 발생했습니다.", e);
        }
    }

    // 회원 탈퇴 메서드
    public void deleteUserById(Long userId) {
        // 사용자가 존재하는지 확인 후 삭제
        userRepository.findById(userId).ifPresentOrElse(
                userRepository::delete,
                () -> { throw new EntityNotFoundException("User with ID " + userId + " not found"); }
        );
    }

    // 역할 변경 메서드
    public void updateUserRole(Long userId, Role newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found")); // 사용자 확인
        user.setRole(newRole); // 역할 변경
        userRepository.save(user); // 저장
    }
}
