package com.memo.gymapi.tutors.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class TutorForListDto {
    String name;
    Float score;
    List<String> subjectNames;
}