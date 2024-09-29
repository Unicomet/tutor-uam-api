package com.memo.gymapi.tutorships.dto;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EvaluationTutorRequest {
    Integer rating;
    String description;
    Integer tutorshipId;
}
