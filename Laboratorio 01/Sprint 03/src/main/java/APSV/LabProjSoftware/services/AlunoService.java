package APSV.LabProjSoftware.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import APSV.LabProjSoftware.entities.Aluno;
import APSV.LabProjSoftware.entities.Disciplina;
import APSV.LabProjSoftware.repositories.AlunoRepository;
import APSV.LabProjSoftware.repositories.DisciplinaRepository;

@Service
public class AlunoService {

  @Autowired
  private AlunoRepository alunoRepository;

  @Autowired
  private DisciplinaRepository disciplinaRepository;

  public List<Aluno> listarAlunos() {
    return alunoRepository.findAll();
  }

  public List<Disciplina> consultarDisciplinas(Long alunoId) {
    Aluno aluno = alunoRepository.findById(alunoId).orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
    return aluno.consultarDisciplinas();
  }

  public Aluno cadastrarAluno(Aluno aluno) {
    return alunoRepository.save(aluno);
  }

  public List<Aluno> cadastrarAlunos(List<Aluno> alunos) {
    return alunoRepository.saveAll(alunos);
  }

  public Aluno matricular(Long alunoId, Long disciplinaId) {
    Aluno aluno = alunoRepository.findById(alunoId).orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
    Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
        .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));
    aluno.addDisciplina(disciplina);
    return alunoRepository.save(aluno);
  }

  public Aluno desmatricular(Long alunoId, Long disciplinaId) {
    Aluno aluno = alunoRepository.findById(alunoId).orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
    Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
        .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));
    aluno.removeDisciplina(disciplina);
    return alunoRepository.save(aluno);
  }
}
