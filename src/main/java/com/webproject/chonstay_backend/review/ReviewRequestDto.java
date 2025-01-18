package com.webproject.chonstay_backend.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ReviewRequestDto {

    @NotBlank(message = "Review title cannot be empty.")
    private String reviewTitle;

    @NotBlank(message = "Review body cannot be empty.")
    private String reviewBody;

    @Min(1) @Max(5)
    private Integer point;

    private Long homeId;

    // Constructor
    public ReviewRequestDto(String reviewTitle, String reviewBody, Integer point, Long homeId) {
        this.reviewTitle = reviewTitle;
        this.reviewBody = reviewBody;
        this.point = point;
        this.homeId = homeId;
    }
}
