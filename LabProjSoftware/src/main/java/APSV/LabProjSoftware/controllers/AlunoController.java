package APSV.LabProjSoftware.controllers;

import APSV.LabProjSoftware.models.Aluno;
import APSV.LabProjSoftware.models.Disciplina;
import APSV.LabProjSoftware.repositories.AlunoRepository;
import APSV.LabProjSoftware.repositories.DisciplinaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

  @Autowired
  private AlunoRepository alunoRepository;

  @Autowired
  private DisciplinaRepository disciplinaRepository;

  @GetMapping
  public List<Aluno> listarAlunos() {
    return alunoRepository.findAll();
  }

  @GetMapping("/disciplinas")
  public List<Disciplina> consultarDisciplinas(@RequestParam Long alunoId) {
    Aluno aluno = alunoRepository.findById(alunoId).get();
    return aluno.consultarDisciplinas();
  }

  @PostMapping
  public Aluno cadastrarAluno(@RequestBody Aluno aluno) {
    return alunoRepository.save(aluno);
  }

  @PostMapping("/lote")
  public List<Aluno> cadastrarAlunos(@RequestBody List<Aluno> alunos) {
    return alunoRepository.saveAll(alunos);
  }

  @PostMapping("/matricular")
  public Aluno matricular(@RequestParam Long alunoId, @RequestParam Long disciplinaId) {
    Aluno aluno = alunoRepository.findById(alunoId).get();
    Disciplina disciplina = disciplinaRepository.findById(disciplinaId).get();
    aluno.addDisciplina(disciplina);
    return alunoRepository.save(aluno);
  }

  @PostMapping("/desmatricular")
  public Aluno desmatricular(@RequestParam Long alunoId, @RequestParam Long disciplinaId) {
    Aluno aluno = alunoRepository.findById(alunoId).get();
    Disciplina disciplina = disciplinaRepository.findById(disciplinaId).get();
    aluno.removeDisciplina(disciplina);
    return alunoRepository.save(aluno);
  }

}
