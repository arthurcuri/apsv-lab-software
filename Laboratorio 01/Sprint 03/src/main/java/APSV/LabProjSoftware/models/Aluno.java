package APSV.LabProjSoftware.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.util.List;

@Entity
public class Aluno extends Usuario {
    @ManyToMany
    private List<Disciplina> disciplinas;

    public void cancelarMatricula(Disciplina d) {}
    public void inscreverEmDisciplina(Disciplina d) {}
    public List<Disciplina> visualizarDisciplinas() { return null; }
}
