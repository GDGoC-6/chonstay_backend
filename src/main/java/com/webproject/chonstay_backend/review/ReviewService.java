package com.webproject.chonstay_backend.review;

import com.webproject.chonstay_backend.home.Home;
import com.webproject.chonstay_backend.home.HomeRepository;
import com.webproject.chonstay_backend.user.User;
import com.webproject.chonstay_backend.user.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final HomeRepository homeRepository;

    public Review createReview(@Valid ReviewRequestDto reviewRequestDto, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        Home home = homeRepository.findById(reviewRequestDto.getHomeId())
                .orElseThrow(() -> new IllegalArgumentException("Home not found with id: " + reviewRequestDto.getHomeId()));

        // Review 객체 생성
        Review review = new Review();
        review.setReviewTitle(reviewRequestDto.getReviewTitle());
        review.setReviewBody(reviewRequestDto.getReviewBody());
        review.setPoint(reviewRequestDto.getPoint());
        review.setUser(user);  // 리뷰에 사용자 정보 연결
        review.setHome(home);

        return reviewRepository.save(review);
    }
}
