package APSV.LabProjSoftware.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Curso {
    @Id
    private Long id;
    private String nome;
    private int totalCreditos;

    @OneToMany
    private List<Disciplina> disciplinas;

    public void adicionarDisciplina(Disciplina d) {}
    public void removerDisciplina(Disciplina d) {}
}
