package APSV.LabProjSoftware.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Disciplina {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nome;
  private Integer creditos;
  private Integer maxAlunos = 60;
  private Integer minAlunos = 3;
  private Boolean ehAtiva = true;

  @ManyToOne
  @JsonBackReference
  private Professor professor;

  @ManyToMany(mappedBy = "disciplinas")
  private List<Aluno> alunos = new ArrayList<>();

  public Disciplina() {
  }

  public Disciplina(String nome, Integer creditos, Integer maxAlunos, Integer minAlunos, Boolean ehAtiva,
      Professor professor) {
    this.nome = nome;
    this.creditos = creditos;
    this.maxAlunos = maxAlunos;
    this.minAlunos = minAlunos;
    this.ehAtiva = ehAtiva;
    this.professor = professor;
  }

  // Getters e Setters
  public Long getId() {
    return id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public int getCreditos() {
    return creditos;
  }

  public void setCreditos(int creditos) {
    this.creditos = creditos;
  }

  public int getMaxAlunos() {
    return maxAlunos;
  }

  public void setMaxAlunos(int maxAlunos) {
    this.maxAlunos = maxAlunos;
  }

  public int getMinAlunos() {
    return minAlunos;
  }

  public void setMinAlunos(int minAlunos) {
    this.minAlunos = minAlunos;
  }

  public Boolean getEhAtiva() {
    return ehAtiva;
  }

  public void setEhAtiva(Boolean ehAtiva) {
    this.ehAtiva = ehAtiva;
  }

  public Professor getProfessor() {
    return professor;
  }

  public void setProfessor(Professor professor) {
    if (this.professor != null && professor == null) {
      Professor oldProfessor = this.professor;
      this.professor = null;
      oldProfessor.removeDisciplina(this);
    }
    else if (this.professor != professor) {
      if (this.professor != null) {
        this.professor.removeDisciplina(this);
      }
      this.professor = professor;
      if (professor != null && !professor.getDisciplinas().contains(this)) {
        professor.addDisciplina(this);
      }
    }
  }

  public List<Aluno> getAlunos() {
    return alunos;
  }

  public int getTotalAlunos() {
    return alunos.size();
  }

  public void addAluno(Aluno aluno) {
    if (!verificarCapacidade()) {
      throw new RuntimeException("Limite de alunos atingido");
    } else if (verificarSeAlunoJaEstaMatriculado(aluno)) {
      throw new RuntimeException("Aluno já matriculado");
    } else if (!ehAtiva) {
      throw new RuntimeException("Disciplina inativa");
    } else {
      alunos.add(aluno);
    }
  }

  public void removeAluno(Aluno aluno) {
    alunos.remove(aluno);
  }

  public boolean verificarMinimoAlunos() {
    if (alunos.size() < 3) {
      return false;
    }
    return true;
  }

  public boolean verificarCapacidade() {
    if (alunos.size() >= maxAlunos) {
      return false;
    }
    return true;
  }

  public boolean verificarSeAlunoJaEstaMatriculado(Aluno aluno) {
    if (alunos.stream().anyMatch(a -> a.getId().equals(aluno.getId()))) {
      return true;
    }
    return false;
  }

  public void desativar() {
    this.ehAtiva = false;
    alunos.stream().forEach(a -> a.removeDisciplina(this));
    this.alunos.clear();
  }

  @Override
  public String toString() {
    return "Disciplina [alunos=" + alunos + ", creditos=" + creditos + ", ehAtiva=" + ehAtiva + ", id=" + id
        + ", maxAlunos=" + maxAlunos + ", minAlunos=" + minAlunos + ", nome=" + nome + ", professor=" + professor + "]";
  }

}
