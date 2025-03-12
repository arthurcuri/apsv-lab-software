package APSV.LabProjSoftware.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import APSV.LabProjSoftware.entities.Disciplina;
import APSV.LabProjSoftware.entities.Professor;
import APSV.LabProjSoftware.services.ProfessorService;

@RestController
@RequestMapping("/professor")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping
    public List<Professor> listarProfessores() {
        return professorService.listarProfessores();
    }

    @GetMapping("/disciplinas")
    public List<Disciplina> listarDisciplinas(@RequestParam Long professorId) {
        return professorService.listarDisciplinas(professorId);
    }

    @GetMapping("/{professorId}/disciplinas")
    public List<Disciplina> getDisciplinasByProfessorId(@PathVariable Long professorId) {
        return professorService.getDisciplinasByProfessorId(professorId);
    }

    @PostMapping
    public Professor cadastrarProfessor(@RequestBody Professor professor) {
        return professorService.cadastrarProfessor(professor);
    }

    @PostMapping("/lote")
    public List<Professor> cadastrarProfessores(@RequestBody List<Professor> professores) {
        return professorService.cadastrarProfessores(professores);
    }

    @PostMapping("/adicionar-disciplina")
    public Professor adicionarDisciplina(@RequestParam Long professorId, @RequestParam Long disciplinaId) {
        return professorService.adicionarDisciplina(professorId, disciplinaId);
    }

    @PostMapping("/remover-disciplina")
    public Professor removerDisciplina(@RequestParam Long professorId, @RequestParam Long disciplinaId) {
        return professorService.removerDisciplina(professorId, disciplinaId);
    }
}
