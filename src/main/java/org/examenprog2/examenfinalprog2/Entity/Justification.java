package org.examenprog2.examenfinalprog2.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@AllArgsConstructor
@Setter
@Getter
public class Justification {
    private int id;
    private LocalDateTime justification_date;
    private String support_doc;
}
