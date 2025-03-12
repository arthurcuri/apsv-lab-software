package APSV.LabProjSoftware.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import APSV.LabProjSoftware.entities.Curso;
import APSV.LabProjSoftware.entities.Disciplina;
import APSV.LabProjSoftware.services.CursoService;

@RestController
@RequestMapping("/curso")
public class CursoController {

  @Autowired
  private CursoService cursoService;

  @GetMapping
  public List<Curso> listarCursos() {
    return cursoService.listarCursos();
  }

  @GetMapping("/disciplinas")
  public List<Disciplina> listarDisciplinas(@RequestParam Long cursoId) {
    return cursoService.listarDisciplinas(cursoId);
  }

  @GetMapping("/{cursoId}/disciplinas")
  public List<Disciplina> getDisciplinasByCursoId(@PathVariable Long cursoId) {
    return cursoService.getDisciplinasByCursoId(cursoId);
  }

  @PostMapping
  public Curso cadastrarCurso(@RequestBody Curso curso) {
    return cursoService.cadastrarCurso(curso);
  }

  @PostMapping("/add-disciplina")
  public Curso addDisciplina(@RequestParam Long cursoId, @RequestParam Long disciplinaId) {
    return cursoService.addDisciplina(cursoId, disciplinaId);
  }
}
