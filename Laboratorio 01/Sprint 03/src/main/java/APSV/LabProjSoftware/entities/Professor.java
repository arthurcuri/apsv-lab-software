package APSV.LabProjSoftware.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Professor extends User {

  @OneToMany
  @JsonManagedReference
  private List<Disciplina> disciplinasLecionadas = new ArrayList<>();

  public Professor() {
  }

  public Professor(String nome, String email, String senha) {
    super(nome, email, senha);
  }

  public List<Disciplina> getDisciplinas() {
    return new ArrayList<>(disciplinasLecionadas);
  }

  public void addDisciplina(Disciplina disciplina) {
    if (!disciplinasLecionadas.contains(disciplina)) {
      disciplinasLecionadas.add(disciplina);
      if (disciplina.getProfessor() != this) {
        disciplina.setProfessor(this);
      }
    }
  }

  public void removeDisciplina(Disciplina disciplina) {
    disciplinasLecionadas.remove(disciplina);
  }

  public List<Aluno> visualizarAlunosMatriculados(Disciplina disciplina) {
    return disciplina.getAlunos();
  }

  @Override
  public String toString() {
    return "Professor [disciplinasLecionadas=" + disciplinasLecionadas + "]";
  }
}
