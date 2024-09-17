package org.examenprog2.examenfinalprog2.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
@Getter
public class Absence {
    private int id;
    private LocalDateTime absence_date;
    private boolean is_accepted;
    private Student student_absence;
    private Course course_absence;
    private Justification justification_absence;
}
