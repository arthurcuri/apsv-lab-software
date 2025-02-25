package APSV.LabProjSoftware.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Professor extends Usuario {
    @OneToMany(mappedBy = "professorResponsavel")
    private List<Disciplina> disciplinasLecionadas;

    public List<Aluno> consultarMatricula(Disciplina d) { return null; }
}
