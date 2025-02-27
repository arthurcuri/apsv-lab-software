package APSV.LabProjSoftware.controllers;

import APSV.LabProjSoftware.models.Disciplina;
import APSV.LabProjSoftware.services.DisciplinaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {
    private final DisciplinaService disciplinaService;

    public DisciplinaController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @GetMapping
    public List<Disciplina> listarDisciplinas() {
        return disciplinaService.listarDisciplinas();
    }
}
