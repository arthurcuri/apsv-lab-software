package APSV.LabProjSoftware.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import APSV.LabProjSoftware.entities.Disciplina;
import APSV.LabProjSoftware.services.DisciplinaService;

@RestController
@RequestMapping("/disciplina")
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    @GetMapping
    public List<Disciplina> listarDisciplinas() {
        return disciplinaService.listarDisciplinas();
    }

    @GetMapping("/{id}")
    public Disciplina getDisciplina(@PathVariable Long id) {
        return disciplinaService.getDisciplina(id);
    }

    @GetMapping("/total-alunos")
    public Integer totalAlunos(@RequestParam Long disciplinaId) {
        return disciplinaService.totalAlunos(disciplinaId);
    }

    @PostMapping
    public Disciplina cadastrarDisciplina(@RequestBody Disciplina disciplina) {
        return disciplinaService.cadastrarDisciplina(disciplina);
    }
}
