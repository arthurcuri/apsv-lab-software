package APSV.LabProjSoftware.controllers;

import APSV.LabProjSoftware.models.Curso;
import APSV.LabProjSoftware.models.Disciplina;
import APSV.LabProjSoftware.repositories.CursoRepository;
import APSV.LabProjSoftware.repositories.DisciplinaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/curso")
public class CursoController {

  @Autowired
  private CursoRepository cursoRepository;

  @Autowired
  private DisciplinaRepository disciplinaRepository;

  @GetMapping
  public List<Curso> listarCursos() {
    return cursoRepository.findAll();
  }

  @GetMapping("/disciplinas")
  public List<Disciplina> listarDisciplinas(@RequestParam Long cursoId) {
    Curso curso = cursoRepository.findById(cursoId)
        .orElseThrow(() -> new RuntimeException("Curso não encontrado"));
    return curso.getDisciplinas();
  }

  @GetMapping("/{cursoId}/disciplinas")
  public List<Disciplina> getDisciplinasByCursoId(@PathVariable Long cursoId) {
    Curso curso = cursoRepository.findById(cursoId)
        .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

    List<Disciplina> disciplinas = curso.getDisciplinas();

    // Obter as disciplinas diretamente do repositório para garantir serialização
    // correta
    List<Disciplina> disciplinasCompletas = new ArrayList<>();
    for (Disciplina d : disciplinas) {
      disciplinaRepository.findById(d.getId()).ifPresent(disciplinasCompletas::add);
    }

    return disciplinasCompletas;
  }

  @PostMapping
  public Curso cadastrarCurso(@RequestBody Curso curso) {
    return cursoRepository.save(curso);
  }

  @PostMapping("/add-disciplina")
  public Curso addDisciplina(@RequestParam Long cursoId, @RequestParam Long disciplinaId) {
    Curso curso = cursoRepository.findById(cursoId).get();
    Disciplina disciplina = disciplinaRepository.findById(disciplinaId).get();
    curso.addDisciplina(disciplina);
    return cursoRepository.save(curso);
  }
}
