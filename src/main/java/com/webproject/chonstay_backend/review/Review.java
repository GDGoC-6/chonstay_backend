package com.webproject.chonstay_backend.review;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.webproject.chonstay_backend.common.BaseEntity;
import com.webproject.chonstay_backend.home.Home;
import com.webproject.chonstay_backend.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private String reviewId;

    @Column(nullable = false)
    private String reviewTitle;

    @Column(columnDefinition = "text", nullable = false)
    private String reviewBody;

    @Min(1)
    @Max(5)
    private Integer point;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_id", nullable = false)
    private Home home;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
