package com.finalproject.vaccine_management.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class FeedbackResponse {
    String id;
    String userId;
    String userName;
    String medicalRecordId;
    String vaccineId;
    String vaccineName;
    Integer rating;
    String comment;
}
