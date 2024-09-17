package org.examenprog2.examenfinalprog2.Repository;

import lombok.AllArgsConstructor;
import org.examenprog2.examenfinalprog2.Entity.Justification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class JustificationImpl {
    private Connection connection;


    public List<Justification> getAllJustifications() {
        List<Justification> justifications = new ArrayList<>();
        String query = "SELECT * FROM justification";

        try (PreparedStatement stm = this.connection.prepareStatement(query)) {
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                justifications.add(new Justification(
                        rs.getInt("id_justification"),
                        rs.getObject("justification_date", LocalDateTime.class),
                        rs.getString("support_doc")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return justifications;
    }

    public Justification getJustificationById(int id) {
        String query = "SELECT * FROM justification WHERE id_justification = ?";

        try (PreparedStatement stm = this.connection.prepareStatement(query)) {
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                return new Justification(
                        rs.getInt("id_justification"),
                        rs.getObject("justification_date", LocalDateTime.class),
                        rs.getString("support_doc")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void addJustification(Justification justification) {
        String query = "INSERT INTO justification (justification_date, support_doc) VALUES (?, ?)";
        try (PreparedStatement stm = this.connection.prepareStatement(query)) {
            stm.setObject(1, justification.getJustification_date());
            stm.setString(2, justification.getSupport_doc());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateJustification(Justification justification) {
        String query = "UPDATE justification SET justification_date = ?, support_doc = ? WHERE id_justification = ?";
        try (PreparedStatement stm = this.connection.prepareStatement(query)) {
            stm.setObject(1, justification.getJustification_date());
            stm.setString(2, justification.getSupport_doc());
            stm.setInt(3, justification.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteJustification(int id) {
        String query = "DELETE FROM justification WHERE id_justification = ?";
        try (PreparedStatement stm = this.connection.prepareStatement(query)) {
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
