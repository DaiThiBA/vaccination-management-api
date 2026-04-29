package com.finalproject.vaccine_management.service.Impl;

import com.finalproject.vaccine_management.dto.request.feedback.FeedbackCreationRequest;
import com.finalproject.vaccine_management.dto.request.feedback.FeedbackFilterRequest;
import com.finalproject.vaccine_management.dto.response.FeedbackResponse;
import com.finalproject.vaccine_management.entity.Feedback;
import com.finalproject.vaccine_management.entity.User;
import com.finalproject.vaccine_management.exception.AppException;
import com.finalproject.vaccine_management.exception.ErrorCode;
import com.finalproject.vaccine_management.mapper.FeedbackMapper;
import com.finalproject.vaccine_management.repository.IFeedbackRepository;
import com.finalproject.vaccine_management.repository.IMedicalRepository;
import com.finalproject.vaccine_management.repository.IUserRepository;
import com.finalproject.vaccine_management.service.IFeedbackService;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FeedbackService implements IFeedbackService {

    IMedicalRepository medicalRepository;
    FeedbackMapper feedbackMapper;
    IFeedbackRepository feedbackRepository;
    IUserRepository userRepository;

    @Override
    @Transactional
    public FeedbackResponse create(FeedbackCreationRequest request) {

        var authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        String userAutheticationName = authentication.getName();

        User user = userRepository.findByUsername(userAutheticationName)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));


        var medicalRecord = medicalRepository.findById(request.getMedicalRecordId())
                .orElseThrow(() -> new AppException(ErrorCode.MEDICAL_RECORD_NOT_FOUND));

        Feedback feedback = feedbackMapper.fromCreate(request);

        feedback.setMedicalRecord(medicalRecord);
        feedback.setUser(user);

        return feedbackMapper.toResponse(feedbackRepository.save(feedback));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FeedbackResponse> filter(FeedbackFilterRequest filter, Pageable pageable) {
        Specification<Feedback> spec = buildFilter(filter);

        Page<Feedback> feedbackPage = feedbackRepository.findAll(spec, pageable);

        List<FeedbackResponse> list = new ArrayList<>();

        for(Feedback feedback : feedbackPage.getContent()){
            list.add(feedbackMapper.toResponse(feedback));
        }

        return new PageImpl<>(list, pageable, feedbackPage.getTotalElements());
    }


    public static Specification<Feedback> buildFilter(FeedbackFilterRequest request) {

        if (request == null){
            return (root,query, cb) -> cb.conjunction();
        }

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (request.getUserId() != null && !request.getUserId().isBlank()) {
                predicates.add(
                        cb.equal(root.get("user").get("id"), request.getUserId())
                );
            }

            //Ưu tiên exact rating
            if (request.getRating() != null) {
                predicates.add(
                        cb.equal(root.get("rating"), request.getRating())
                );
            }

            else if (request.getRatingRange() != null) {

                Integer min = request.getRatingRange().getMin();
                Integer max = request.getRatingRange().getMax();
                    if (min != null) {
                        predicates.add( cb.greaterThanOrEqualTo(
                                        root.get("rating"), min)
                        );
                    }

                    if (max != null) {
                        predicates.add(
                                cb.lessThanOrEqualTo(
                                        root.get("rating"),
                                        max
                                )
                        );
                    }
            }

            //
            if (request.getFromDate() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(
                                root.get("createdAt"),
                                request.getFromDate().atStartOfDay()
                        )
                );
            }

            if (request.getToDate() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(
                                root.get("createdAt"),
                                request.getToDate().atTime(23, 59, 59)
                        )
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
