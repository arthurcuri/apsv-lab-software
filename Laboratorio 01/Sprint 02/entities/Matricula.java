package APSV.LabProjSoftware.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private boolean confirmada;

    @ManyToOne
    private Aluno aluno;
    @ManyToOne
    private Disciplina disciplina;
}
