package APSV.LabProjSoftware.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Matricula {
    @Id
    private String id;
    private boolean confirmada;

    @ManyToOne
    private Aluno aluno;
    @ManyToOne
    private Disciplina disciplina;
}
