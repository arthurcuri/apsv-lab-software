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
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private int totalCreditos;

    @OneToMany
    private List<Disciplina> disciplinas;

    public void adicionarDisciplina(Disciplina d) {}
    public void removerDisciplina(Disciplina d) {}
}
