package com.memo.gymapi.tutors.dto;

import com.memo.gymapi.tutors.model.Day;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Data
@Getter
@Setter
public class AvailabilityResponse {
    Day day;
    LocalTime startTime;
    LocalTime endTime;
}
