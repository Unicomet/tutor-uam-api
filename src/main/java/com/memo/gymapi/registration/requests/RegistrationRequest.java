package com.memo.gymapi.registration.requests;

import com.memo.gymapi.registration.model.EducationLevel;
import com.memo.gymapi.user.Faculty;
import com.memo.gymapi.user.Ocupation;
import com.memo.gymapi.user.Role;
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
}
