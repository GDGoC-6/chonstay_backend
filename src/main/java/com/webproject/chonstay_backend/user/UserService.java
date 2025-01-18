package com.webproject.chonstay_backend.user;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원가입
    public void registerUser(User user) {
        try {
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

    // SHA-256 비밀번호 암호화
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("비밀번호 암호화 중 문제가 발생했습니다.", e);
        }
    }
}
