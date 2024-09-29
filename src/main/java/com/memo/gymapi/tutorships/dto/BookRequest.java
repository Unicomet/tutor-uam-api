package com.memo.gymapi.tutorships.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {
    LocalDate date;
    LocalTime startTime;
    Integer tutorId;
}
