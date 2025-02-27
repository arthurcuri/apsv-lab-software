package APSV.LabProjSoftware.models;

import jakarta.persistence.*;
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
public class Disciplina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
