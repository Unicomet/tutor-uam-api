package com.memo.gymapi.subjects.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public  class DayAvailability {
    // Getters and Setters
    private boolean enabled;
    private List<Period> periods;
}