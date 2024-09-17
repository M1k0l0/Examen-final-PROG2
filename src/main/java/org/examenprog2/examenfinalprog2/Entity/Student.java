package org.examenprog2.examenfinalprog2.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class Student {
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String adress;
    private String reference;
    private String level;
    private Group student_group;

}
