package APSV.LabProjSoftware.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Administrativo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nome;

  public Administrativo() {
  }

  public Administrativo(String nome) {
    this.nome = nome;
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

  public void verificarDisciplinasAtivas() {
    // Implementar lógica para verificar disciplinas ativas
  }

  public void gerarCurriculo(Aluno aluno) {
    // Implementar lógica para gerar currículo do aluno
  }

  public void manterCursos(Curso curso) {
    // Implementar lógica para manter cursos
  }

  public void manterDisciplinas(Curso curso, Disciplina disciplina) {
    // Implementar lógica para manter disciplinas de um curso
  }
}
