package org.examenprog2.examenfinalprog2.Repository;

import lombok.AllArgsConstructor;
import org.examenprog2.examenfinalprog2.Entity.Course;
import org.examenprog2.examenfinalprog2.Entity.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class CourseImpl {
    private Connection connection;

    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT c.*, t.* " +
                "FROM course c " +
                "JOIN teacher t ON c.id_teacher = t.id_teacher";

        try (PreparedStatement stm = this.connection.prepareStatement(query)) {
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                courses.add(new Course(
                        rs.getInt("c.id_course"),
                        rs.getString("c.course_name"),
                        new Teacher(
                                rs.getInt("t.id_teacher"),
                                rs.getString("t.first_name"),
                                rs.getString("t.last_name"),
                                rs.getString("t.email")
                        )
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courses;
    }

    public Course getCourseById(int id) {
        String query = "SELECT c.*, t.* " +
                "FROM course c " +
                "JOIN teacher t ON c.id_teacher = t.id_teacher " +
                "WHERE c.id_course = ?";

        try (PreparedStatement stm = this.connection.prepareStatement(query)) {
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                return new Course(
                        rs.getInt("c.id_course"),
                        rs.getString("c.course_name"),
                        new Teacher(
                                rs.getInt("t.id_teacher"),
                                rs.getString("t.first_name"),
                                rs.getString("t.last_name"),
                                rs.getString("t.email")
                        )
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void addCourse(Course course) {
        String query = "INSERT INTO course (course_name, id_teacher) VALUES (?, ?)";
        try (PreparedStatement stm = this.connection.prepareStatement(query)) {
            stm.setString(1, course.getCourse_name());
            stm.setInt(2, course.getTeacher().getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCourse(Course course) {
        String query = "UPDATE course SET course_name = ?, id_teacher = ? WHERE id_course = ?";
        try (PreparedStatement stm = this.connection.prepareStatement(query)) {
            stm.setString(1, course.getCourse_name());
            stm.setInt(2, course.getTeacher().getId());
            stm.setInt(3, course.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCourse(int id) {
        String query = "DELETE FROM course WHERE id_course = ?";
        try (PreparedStatement stm = this.connection.prepareStatement(query)) {
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

