package APSV.LabProjSoftware.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Secretaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public void gerarCurriculo() {}

    public List<Disciplina> verificarDisciplinasAtivas() { return null; }

    public void manterCursos(Curso c) {}

    public void manterDisciplinas(Curso c, Disciplina d) {}
}
