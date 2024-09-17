package org.examenprog2.examenfinalprog2.Repository;

import org.examenprog2.examenfinalprog2.Entity.Absence;

import java.util.List;
import java.util.Optional;

public interface AbsenceDAO {
    List<Absence> getAllStudentAbsence();//Liste de tout les eleves absents.

    Optional<Absence> getStudentAbsenceById(int id);//Avoir un eleve absent a l'aide de son id.
}
