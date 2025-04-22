package APSV.LabProjSoftware.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import APSV.LabProjSoftware.entities.Curso;
import APSV.LabProjSoftware.entities.Disciplina;
import APSV.LabProjSoftware.repositories.CursoRepository;
import APSV.LabProjSoftware.repositories.DisciplinaRepository;

@Service
public class CursoService {

  @Autowired
  private CursoRepository cursoRepository;

  @Autowired
  private DisciplinaRepository disciplinaRepository;

  public List<Curso> listarCursos() {
    return cursoRepository.findAll();
  }

  public List<Disciplina> listarDisciplinas(Long cursoId) {
    Curso curso = cursoRepository.findById(cursoId)
        .orElseThrow(() -> new RuntimeException("Curso não encontrado"));
    return curso.getDisciplinas();
  }

  public List<Disciplina> getDisciplinasByCursoId(Long cursoId) {
    Curso curso = cursoRepository.findById(cursoId)
        .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

    List<Disciplina> disciplinas = curso.getDisciplinas();
    List<Disciplina> disciplinasCompletas = new ArrayList<>();
    
    for (Disciplina d : disciplinas) {
      disciplinaRepository.findById(d.getId()).ifPresent(disciplinasCompletas::add);
    }

    return disciplinasCompletas;
  }

  public Curso cadastrarCurso(Curso curso) {
    return cursoRepository.save(curso);
  }

  public Curso addDisciplina(Long cursoId, Long disciplinaId) {
    Curso curso = cursoRepository.findById(cursoId)
        .orElseThrow(() -> new RuntimeException("Curso não encontrado"));
    Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
        .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));
    
    curso.addDisciplina(disciplina);
    return cursoRepository.save(curso);
  }
}
