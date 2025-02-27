package APSV.LabProjSoftware.repositories;

import APSV.LabProjSoftware.models.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}
