package org.examenprog2.examenfinalprog2.Repository;

import lombok.AllArgsConstructor;
import org.examenprog2.examenfinalprog2.Entity.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.Connection;


@AllArgsConstructor
public class AbsenceImpl {
    private Connection connection;

    public List<Absence> getAllAbsences() {
        List<Absence> absences = new ArrayList<>();
        String query = "SELECT a.*, s.*, c.*, j.*, g.group_name, t.first_name AS teacher_first_name, t.last_name AS teacher_last_name " +
                "FROM absence a " +
                "JOIN student s ON a.id_student = s.id_student " +
                "JOIN course c ON a.id_course = c.id_course " +
                "LEFT JOIN justification j ON a.id_justification = j.id_justification " +
                "JOIN \"group\" g ON s.id_group = g.id_group " +
                "JOIN teacher t ON c.id_teacher = t.id_teacher";

        try (PreparedStatement stm = this.connection.prepareStatement(query)) {
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Absence absence = new Absence(
                        rs.getInt("a.id_absence"),
                        rs.getObject("a.absence_date", LocalDateTime.class),
                        rs.getBoolean("a.is_accepted"),
                        new Student(
                                rs.getInt("s.id_student"),
                                rs.getString("s.first_name"),
                                rs.getString("s.last_name"),
                                rs.getString("s.email"),
                                rs.getString("s.adress"),
                                rs.getString("s.reference"),
                                rs.getString("s.level"),
                                new Group(
                                        rs.getInt("s.id_group"),
                                        rs.getString("g.group_name")
                                )
                        ),
                        new Course(
                                rs.getInt("c.id_course"),
                                rs.getString("c.course_name"),
                                new Teacher(
                                        rs.getInt("c.id_teacher"),
                                        rs.getString("teacher_first_name"),
                                        rs.getString("teacher_last_name"),
                                        rs.getString("c.id_teacher")
                                )
                        ),
                        rs.getInt("j.id_justification") > 0 ? new Justification(
                                rs.getInt("j.id_justification"),
                                rs.getObject("j.justification_date", LocalDateTime.class),
                                rs.getString("j.support_doc")
                        ) : null
                );
                absences.add(absence);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return absences;
    }

    public Absence getAbsenceById(int id) {
        String query = "SELECT a.*, s.*, c.*, j.*, g.group_name, t.first_name AS teacher_first_name, t.last_name AS teacher_last_name " +
                "FROM absence a " +
                "JOIN student s ON a.id_student = s.id_student " +
                "JOIN course c ON a.id_course = c.id_course " +
                "LEFT JOIN justification j ON a.id_justification = j.id_justification " +
                "JOIN \"group\" g ON s.id_group = g.id_group " +
                "JOIN teacher t ON c.id_teacher = t.id_teacher " +
                "WHERE a.id_absence = ?";

        try (PreparedStatement stm = this.connection.prepareStatement(query)) {
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                return new Absence(
                        rs.getInt("a.id_absence"),
                        rs.getObject("a.absence_date", LocalDateTime.class),
                        rs.getBoolean("a.is_accepted"),
                        new Student(
                                rs.getInt("s.id_student"),
                                rs.getString("s.first_name"),
                                rs.getString("s.last_name"),
                                rs.getString("s.email"),
                                rs.getString("s.adress"),
                                rs.getString("s.reference"),
                                rs.getString("s.level"),
                                new Group(
                                        rs.getInt("s.id_group"),
                                        rs.getString("g.group_name")
                                )
                        ),
                        new Course(
                                rs.getInt("c.id_course"),
                                rs.getString("c.course_name"),
                                new Teacher(
                                        rs.getInt("c.id_teacher"),
                                        rs.getString("teacher_first_name"),
                                        rs.getString("teacher_last_name"),
                                        rs.getString("c.id_teacher")
                                )
                        ),
                        rs.getInt("j.id_justification") > 0 ? new Justification(
                                rs.getInt("j.id_justification"),
                                rs.getObject("j.justification_date", LocalDateTime.class),
                                rs.getString("j.support_doc")
                        ) : null
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void addAbsence(Absence absence) {
        String query = "INSERT INTO absence (absence_date, is_accepted, id_student, id_course, id_justification) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stm = this.connection.prepareStatement(query)) {
            stm.setObject(1, absence.getAbsence_date());
            stm.setBoolean(2, absence.is_accepted());
            stm.setInt(3, absence.getStudent_absence().getId());
            stm.setInt(4, absence.getCourse_absence().getId());
            stm.setInt(5, absence.getJustification_absence() != null ? absence.getJustification_absence().getId() : null);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateAbsence(Absence absence) {
        String query = "UPDATE absence SET absence_date = ?, is_accepted = ?, id_student = ?, id_course = ?, id_justification = ? WHERE id_absence = ?";
        try (PreparedStatement stm = this.connection.prepareStatement(query)) {
            stm.setObject(1, absence.getAbsence_date());
            stm.setBoolean(2, absence.is_accepted());
            stm.setInt(3, absence.getStudent_absence().getId());
            stm.setInt(4, absence.getCourse_absence().getId());
            stm.setInt(5, absence.getJustification_absence() != null ? absence.getJustification_absence().getId() : null);
            stm.setInt(6, absence.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAbsence(int id) {
        String query = "DELETE FROM absence WHERE id_absence = ?";
        try (PreparedStatement stm = this.connection.prepareStatement(query)) {
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

