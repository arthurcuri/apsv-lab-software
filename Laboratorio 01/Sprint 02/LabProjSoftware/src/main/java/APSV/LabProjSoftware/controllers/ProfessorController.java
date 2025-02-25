package APSV.LabProjSoftware.controllers;

import APSV.LabProjSoftware.models.Professor;
import APSV.LabProjSoftware.services.ProfessorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/professores")
public class ProfessorController {
    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping
    public List<Professor> listarProfessores() {
        return professorService.listarProfessores();
    }
}
