package com.memo.gymapi.tutorships.dto;

import com.memo.gymapi.tutorships.model.Tutorship;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TutorshipsListResponse {
    List<Tutorship> tutorships;
}
