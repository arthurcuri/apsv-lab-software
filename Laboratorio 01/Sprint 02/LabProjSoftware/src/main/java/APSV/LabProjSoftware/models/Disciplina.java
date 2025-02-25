package APSV.LabProjSoftware.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.util.List;

@Entity
public class Disciplina {
    @Id
    private Long id;
    private String nome;
    private boolean ehObrigatoria;
    private int cargaHoraria;
    private int creditos;
    private int maxAlunos;
    private int minAlunos;

    @ManyToOne
    private Professor professorResponsavel;

    @ManyToMany(mappedBy = "disciplinas")
    private List<Aluno> alunos;

    public List<Aluno> alunosInscritos() { return null; }
    public boolean verificarMinimoAlunos() { return false; }
    public boolean verificarCapacidade() { return false; }
}
