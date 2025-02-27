package APSV.LabProjSoftware.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Professor extends Usuario {
    @OneToMany(mappedBy = "professorResponsavel")
    private List<Disciplina> disciplinasLecionadas;

    public List<Aluno> consultarMatricula(Disciplina d) { return null; }
}
