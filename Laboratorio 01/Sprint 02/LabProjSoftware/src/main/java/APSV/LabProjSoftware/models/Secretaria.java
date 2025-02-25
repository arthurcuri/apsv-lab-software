package APSV.LabProjSoftware.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class Secretaria {
    @Id
    private Long id;

    public void gerarCurriculo() {}
    public List<Disciplina> verificarDisciplinasAtivas() { return null; }
    public void manterCursos(Curso c) {}
    public void manterDisciplinas(Curso c, Disciplina d) {}
}
