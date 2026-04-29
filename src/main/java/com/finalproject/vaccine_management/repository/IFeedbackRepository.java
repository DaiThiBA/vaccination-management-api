package com.finalproject.vaccine_management.repository;

import com.finalproject.vaccine_management.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IFeedbackRepository extends JpaRepository<Feedback, String>
        , JpaSpecificationExecutor<Feedback> {
}
