package APSV.LabProjSoftware.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import APSV.LabProjSoftware.models.Disciplina;
import APSV.LabProjSoftware.models.Professor;
import APSV.LabProjSoftware.repositories.DisciplinaRepository;
import APSV.LabProjSoftware.repositories.ProfessorRepository;

@RestController
@RequestMapping("/professor")
public class ProfessorController {

  private static final Logger LOGGER = Logger.getLogger(ProfessorController.class.getName());

  @Autowired
  private ProfessorRepository professorRepository;

  @Autowired
  private DisciplinaRepository disciplinaRepository;

  @GetMapping
  public List<Professor> listarProfessores() {
    return professorRepository.findAll();
  }

  @GetMapping("/disciplinas")
  public List<Disciplina> listarDisciplinas(@RequestParam Long professorId) {
    Professor professor = professorRepository.findById(professorId)
        .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
    return professor.getDisciplinas();
  }

  @GetMapping("/{professorId}/disciplinas")
  public List<Disciplina> getDisciplinasByProfessorId(@PathVariable Long professorId) {
    LOGGER.info("Buscando disciplinas para o professor com ID: " + professorId);

    Professor professor = professorRepository.findById(professorId)
        .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

    List<Disciplina> disciplinas = professor.getDisciplinas();
    LOGGER.info("Disciplinas encontradas: " + disciplinas.size());

    // Obter as disciplinas diretamente do repositório para garantir serialização
    // correta
    List<Disciplina> disciplinasCompletas = new ArrayList<>();
    for (Disciplina d : disciplinas) {
      disciplinaRepository.findById(d.getId()).ifPresent(disciplinasCompletas::add);
    }

    return disciplinasCompletas;
  }

  @PostMapping
  public Professor cadastrarProfessor(@RequestBody Professor professor) {
    return professorRepository.save(professor);
  }

  @PostMapping("/lote")
  public List<Professor> cadastrarProfessores(@RequestBody List<Professor> professores) {
    return professorRepository.saveAll(professores);
  }

  @PostMapping("/adicionar-disciplina")
  public Professor adicionarDisciplina(@RequestParam Long professorId, @RequestParam Long disciplinaId) {
    LOGGER.info("Adicionando disciplina ID: " + disciplinaId + " ao professor ID: " + professorId);

    Professor professor = professorRepository.findById(professorId)
        .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

    Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
        .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));

    // Estabelecer a relação bidirecional
    professor.addDisciplina(disciplina);
    disciplina.setProfessor(professor);

    // Salvar ambos os objetos
    disciplinaRepository.save(disciplina);
    professor = professorRepository.save(professor);

    // Carregar novamente o professor do repositório para garantir dados
    // consistentes
    return professorRepository.findById(professor.getId())
        .orElseThrow(() -> new RuntimeException("Erro ao recuperar professor após atualização"));
  }

  @PostMapping("/remover-disciplina")
  public Professor removerDisciplina(@RequestParam Long professorId, @RequestParam Long disciplinaId) {
    LOGGER.info("Removendo disciplina ID: " + disciplinaId + " do professor ID: " + professorId);

    Professor professor = professorRepository.findById(professorId)
        .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

    Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
        .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));

    // Remover a relação bidirecional
    professor.removeDisciplina(disciplina);
    disciplina.setProfessor(null);

    // Salvar ambos os objetos
    disciplinaRepository.save(disciplina);
    professor = professorRepository.save(professor);

    // Carregar novamente o professor do repositório para garantir dados
    // consistentes
    return professorRepository.findById(professor.getId())
        .orElseThrow(() -> new RuntimeException("Erro ao recuperar professor após atualização"));
  }

}
