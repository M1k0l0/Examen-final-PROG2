package org.examenprog2.examenfinalprog2.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class Teacher {
    private int id;
    private String first_name;
    private String last_name;
    private String email;
}
