package APSV.LabProjSoftware.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;

@Entity
public class Secretaria extends User {

  private Boolean matriculasAbertas = true;

  public Secretaria() {
  }

  public Secretaria(String nome, String email, String senha) {
    super(nome, email, senha);
  }

  public Boolean getMatriculasAbertas() {
    return matriculasAbertas;
  }

  public void setMatriculasAbertas(Boolean matriculasAbertas) {
    this.matriculasAbertas = matriculasAbertas;
  }

  public void abrirMatriculas() {
    this.matriculasAbertas = true;
  }

  public void fecharMatriculas() {
    this.matriculasAbertas = false;
  }

  public List<Disciplina> fecharPeriodoMatricula(List<Disciplina> disciplinas) {
    List<Disciplina> disciplinasDesativadas = new ArrayList<>();
    for (Disciplina disciplina : disciplinas) {
      if (disciplina.getAlunos().size() < disciplina.getMinAlunos()) {
        disciplina.desativar();
        disciplinasDesativadas.add(disciplina);
      }
    }
    return disciplinasDesativadas;
  }

  @Override
  public String toString() {
    return "Secretaria [id=" + getId() + ", nome=" + getNome() + ", email=" + getEmail() + "]";
  }
}
