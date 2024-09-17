package org.examenprog2.examenfinalprog2.Repository;

import lombok.AllArgsConstructor;
import org.examenprog2.examenfinalprog2.Entity.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class TeacherImpl {

    private final Connection connection;

    public TeacherImpl(Connection connection) {
        this.connection = connection;
    }

    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        String query = "SELECT * FROM teacher";

        try (PreparedStatement stm = this.connection.prepareStatement(query)) {
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                teachers.add(new Teacher(
                        rs.getInt("id_teacher"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return teachers;
    }

    public Teacher getTeacherById(int id) {
        String query = "SELECT * FROM teacher WHERE id_teacher = ?";

        try (PreparedStatement stm = this.connection.prepareStatement(query)) {
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                return new Teacher(
                        rs.getInt("id_teacher"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void addTeacher(Teacher teacher) {
        String query = "INSERT INTO teacher (first_name, last_name, email) VALUES (?, ?, ?)";
        try (PreparedStatement stm = this.connection.prepareStatement(query)) {
            stm.setString(1, teacher.getFirst_name());
            stm.setString(2, teacher.getLast_name());
            stm.setString(3, teacher.getEmail());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTeacher(Teacher teacher) {
        String query = "UPDATE teacher SET first_name = ?, last_name = ?, email = ? WHERE id_teacher = ?";
        try (PreparedStatement stm = this.connection.prepareStatement(query)) {
            stm.setString(1, teacher.getFirst_name());
            stm.setString(2, teacher.getLast_name());
            stm.setString(3, teacher.getEmail());
            stm.setInt(4, teacher.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTeacher(int id) {
        String query = "DELETE FROM teacher WHERE id_teacher = ?";
        try (PreparedStatement stm = this.connection.prepareStatement(query)) {
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

