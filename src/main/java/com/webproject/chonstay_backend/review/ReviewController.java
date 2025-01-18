package com.webproject.chonstay_backend.review;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Review> createReview(
            @RequestBody @Valid ReviewRequestDto reviewRequestDto,
            @RequestHeader("user-id") Long userId) {  // 헤더에서 user-id를 받음

        // 받아온 userId를 사용하여 리뷰 작성
        Review review = reviewService.createReview(reviewRequestDto, userId);
        return ResponseEntity.ok(review);
    }

}
