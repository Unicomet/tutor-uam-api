package com.memo.gymapi.subjects.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrateScheduleRequest {
    private Map<Day, DayAvailability> availability;
    private List<String> subjects;
}