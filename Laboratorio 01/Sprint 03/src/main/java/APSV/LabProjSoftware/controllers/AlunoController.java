package APSV.LabProjSoftware.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import APSV.LabProjSoftware.entities.Aluno;
import APSV.LabProjSoftware.entities.Disciplina;
import APSV.LabProjSoftware.services.AlunoService;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

  @Autowired
  private AlunoService alunoService;

  @GetMapping
  public List<Aluno> listarAlunos() {
    return alunoService.listarAlunos();
  }

  @GetMapping("/disciplinas")
  public List<Disciplina> consultarDisciplinas(@RequestParam Long alunoId) {
    return alunoService.consultarDisciplinas(alunoId);
  }

  @PostMapping
  public Aluno cadastrarAluno(@RequestBody Aluno aluno) {
    return alunoService.cadastrarAluno(aluno);
  }

  @PostMapping("/lote")
  public List<Aluno> cadastrarAlunos(@RequestBody List<Aluno> alunos) {
    return alunoService.cadastrarAlunos(alunos);
  }

  @PostMapping("/matricular")
  public Aluno matricular(@RequestParam Long alunoId, @RequestParam Long disciplinaId) {
    return alunoService.matricular(alunoId, disciplinaId);
  }

  @PostMapping("/desmatricular")
  public Aluno desmatricular(@RequestParam Long alunoId, @RequestParam Long disciplinaId) {
    return alunoService.desmatricular(alunoId, disciplinaId);
  }
}
