package com.finalproject.vaccine_management.service;

import com.finalproject.vaccine_management.dto.request.feedback.FeedbackCreationRequest;
import com.finalproject.vaccine_management.dto.request.feedback.FeedbackFilterRequest;
import com.finalproject.vaccine_management.dto.response.FeedbackResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IFeedbackService {
    FeedbackResponse create(@Valid FeedbackCreationRequest request);

    Page<FeedbackResponse> filter(FeedbackFilterRequest filter, Pageable pageable);
}
