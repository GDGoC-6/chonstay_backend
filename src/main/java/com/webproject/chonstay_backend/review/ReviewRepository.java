package com.webproject.chonstay_backend.review;

import com.webproject.chonstay_backend.home.Home;
import com.webproject.chonstay_backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Custom queries if needed, for example:
    // 1. 특정 user가 작성한 리뷰들 찾기
    List<Review> findByUser(User user);

    // 2. 특정 home에 대한 리뷰들 찾기
    List<Review> findByHome(Home home);

    // 3. 특정 사용자와 특정 home에 대한 리뷰 찾기
    Optional<Review> findByUserAndHome(User user, Home home);
}