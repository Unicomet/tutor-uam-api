package com.memo.gymapi.user.dto;

import com.memo.gymapi.user.model.Faculty;
import com.memo.gymapi.user.model.Ocupation;
import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String firstName;
    private String lastName;
    private Ocupation ocupation;
    private Faculty facultyUAM;
}