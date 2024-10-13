package com.memo.gymapi.auth;

import com.memo.gymapi.user.model.Faculty;
import com.memo.gymapi.user.model.Ocupation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    String firstName;
    String lastName;
    String email;
    String password;
    Faculty faculty;
    Ocupation ocupation;
}
