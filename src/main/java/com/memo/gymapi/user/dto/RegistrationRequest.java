package com.memo.gymapi.user.dto;

import com.memo.gymapi.user.model.EducationLevel;
import com.memo.gymapi.user.model.Faculty;
import com.memo.gymapi.user.model.Ocupation;
import com.memo.gymapi.user.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {
    String description;
    EducationLevel educationLevel;
    Faculty facultyUAM;
    Ocupation ocupation;
    Role roleTutorship;
    String studyField;
    String tutorshipPlace;
}
