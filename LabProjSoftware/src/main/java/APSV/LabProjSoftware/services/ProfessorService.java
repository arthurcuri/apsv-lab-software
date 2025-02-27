package APSV.LabProjSoftware.services;

import APSV.LabProjSoftware.models.Professor;
import APSV.LabProjSoftware.repositories.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {
    private final ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public List<Professor> listarProfessores() {
        return professorRepository.findAll();
    }
}
