package com.webproject.chonstay_backend.review;

import com.webproject.chonstay_backend.home.Home;
import com.webproject.chonstay_backend.home.HomeRepository;
import com.webproject.chonstay_backend.user.User;
import com.webproject.chonstay_backend.user.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final HomeRepository homeRepository;

    public Review createReview(@Valid ReviewRequestDto reviewRequestDto, Long userId) {

        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + userId);
        }

        // 2. 사용자가 작성할 수 있는 'Home'을 조회 (예: 해당 사용자가 예약한 Home을 기준으로)
        Optional<Home> home = homeRepository.findByUserIdAndHomeId(userId, reviewRequestDto.getHomeId());
        if (home.isEmpty()) {
            throw new IllegalArgumentException("Home not found for user with id: " + userId);
        }

        // Review 객체 생성
        Review review = new Review();
        review.setReviewTitle(reviewRequestDto.getReviewTitle());
        review.setReviewBody(reviewRequestDto.getReviewBody());
        review.setPoint(reviewRequestDto.getPoint());
        review.setUser(user.get());  // 리뷰에 사용자 정보 연결
        review.setHome(home.get());

        return reviewRepository.save(review);
    }
}
