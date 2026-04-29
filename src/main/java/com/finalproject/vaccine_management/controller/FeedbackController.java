package com.finalproject.vaccine_management.controller;

import com.finalproject.vaccine_management.dto.request.feedback.FeedbackCreationRequest;
import com.finalproject.vaccine_management.dto.request.feedback.FeedbackFilterRequest;
import com.finalproject.vaccine_management.dto.response.ApiResponse;
import com.finalproject.vaccine_management.dto.response.FeedbackResponse;
import com.finalproject.vaccine_management.entity.Feedback;
import com.finalproject.vaccine_management.service.IFeedbackService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FeedbackController {

    IFeedbackService feedbackService;

    @PostMapping()
    ApiResponse<FeedbackResponse> create(@Valid @RequestBody FeedbackCreationRequest request){
        return ApiResponse.<FeedbackResponse>builder()
                .result(feedbackService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<Page<FeedbackResponse>> filter(
            @ModelAttribute FeedbackFilterRequest filter,
            Pageable pageable
    ){
        return ApiResponse.<Page<FeedbackResponse>>builder()
                .result(feedbackService.filter(filter,pageable))
                .build();
    }


}
