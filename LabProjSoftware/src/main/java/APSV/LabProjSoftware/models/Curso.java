package APSV.LabProjSoftware.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Curso {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nome;
  private Integer totalCreditos = 0;

  @ManyToMany
  @JsonIgnore
  private List<Disciplina> disciplinas = new ArrayList<>();

  public Curso() {
  }

  public Curso(String nome) {
    this.nome = nome;
  }

  public Curso(String nome, Integer totalCreditos) {
    this.nome = nome;
    this.totalCreditos = totalCreditos;
  }

  public Long getId() {
    return id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public Integer getTotalCreditos() {
    return totalCreditos;
  }

  public void setTotalCreditos(Integer totalCreditos) {
    this.totalCreditos = totalCreditos;
  }

  public List<Disciplina> getDisciplinas() {
    return disciplinas;
  }

  public void addDisciplina(Disciplina disciplina) {
    if (!disciplinas.contains(disciplina)) {
      disciplinas.add(disciplina);
      totalCreditos += disciplina.getCreditos();
    }
  }

  public void removeDisciplina(Disciplina disciplina) {
    disciplinas.remove(disciplina);
    totalCreditos -= disciplina.getCreditos();
  }

  @Override
  public String toString() {
    return "Curso [id=" + id + ", nome=" + nome + ", totalCreditos=" + totalCreditos + "]";
  }
}
