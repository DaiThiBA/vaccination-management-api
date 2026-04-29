package com.finalproject.vaccine_management.dto.request.feedback;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackFilterRequest {
    private String userId;        // optional
    private Integer rating;       // optional (lọc đúng 1 mức rating)
    private RatingRange ratingRange; // Range Rating
    private LocalDate fromDate;   // optional
    private LocalDate toDate;     // optional
}
