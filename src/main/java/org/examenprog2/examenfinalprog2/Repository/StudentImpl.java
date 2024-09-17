package org.examenprog2.examenfinalprog2.Repository;

import lombok.AllArgsConstructor;
import org.examenprog2.examenfinalprog2.Entity.Group;
import org.examenprog2.examenfinalprog2.Entity.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;

@AllArgsConstructor
public class StudentImpl{
    private Connection connection;


    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT s.*, g.* " +
                "FROM student s " +
                "JOIN \"group\" g ON s.id_group = g.id_group";

        try (PreparedStatement stm = this.connection.prepareStatement(query)) {
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                students.add(new Student(
                        rs.getInt("s.id_student"),
                        rs.getString("s.first_name"),
                        rs.getString("s.last_name"),
                        rs.getString("s.email"),
                        rs.getString("s.adress"),
                        rs.getString("s.reference"),
                        rs.getString("s.level"),
                        new Group(
                                rs.getInt("g.id_group"),
                                rs.getString("g.group_name")
                        )
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }

    public Student getStudentById(int id) {
        String query = "SELECT s.*, g.* " +
                "FROM student s " +
                "JOIN \"group\" g ON s.id_group = g.id_group " +
                "WHERE s.id_student = ?";

        try (PreparedStatement stm = this.connection.prepareStatement(query)) {
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                return new Student(
                        rs.getInt("s.id_student"),
                        rs.getString("s.first_name"),
                        rs.getString("s.last_name"),
                        rs.getString("s.email"),
                        rs.getString("s.adress"),
                        rs.getString("s.reference"),
                        rs.getString("s.level"),
                        new Group(
                                rs.getInt("g.id_group"),
                                rs.getString("g.group_name")
                        )
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void addStudent(Student student) {
        String query = "INSERT INTO student (first_name, last_name, email, adress, reference, level, id_group) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stm = this.connection.prepareStatement(query)) {
            stm.setString(1, student.getFirst_name());
            stm.setString(2, student.getLast_name());
            stm.setString(3, student.getEmail());
            stm.setString(4, student.getAdress());
            stm.setString(5, student.getReference());
            stm.setString(6, student.getLevel());
            stm.setInt(7, student.getStudent_group().getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateStudent(Student student) {
        String query = "UPDATE student SET first_name = ?, last_name = ?, email = ?, adress = ?, reference = ?, level = ?, id_group = ? WHERE id_student = ?";
        try (PreparedStatement stm = this.connection.prepareStatement(query)) {
            stm.setString(1, student.getFirst_name());
            stm.setString(2, student.getLast_name());
            stm.setString(3, student.getEmail());
            stm.setString(4, student.getAdress());
            stm.setString(5, student.getReference());
            stm.setString(6, student.getLevel());
            stm.setInt(7, student.getStudent_group().getId());
            stm.setInt(8, student.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteStudent(int id) {
        String query = "DELETE FROM student WHERE id_student = ?";
        try (PreparedStatement stm = this.connection.prepareStatement(query)) {
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

