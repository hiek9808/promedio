package com.example.minota.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Grade extends Lesson{

    private Integer idGrade;
    private String nameGrade;
    private Double percent;
    private Double grade;
    private Integer subGrades;
}
