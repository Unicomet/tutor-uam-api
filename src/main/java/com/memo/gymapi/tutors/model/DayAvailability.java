package com.memo.gymapi.tutors.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class DayAvailability {
    // Getters and Setters
    private boolean enabled;
    private List<Period> periods;
}