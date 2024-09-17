package org.examenprog2.examenfinalprog2.Repository;

import org.examenprog2.examenfinalprog2.Entity.Student;

import java.util.List;

public interface StudentDAO {
    List<Student> findAllStudent();

    Student findStudentById(int id);
}
