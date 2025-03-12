package APSV.LabProjSoftware.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import APSV.LabProjSoftware.entities.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {

}
