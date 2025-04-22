package APSV.LabProjSoftware.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import APSV.LabProjSoftware.entities.Disciplina;
import APSV.LabProjSoftware.repositories.DisciplinaRepository;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    public List<Disciplina> listarDisciplinas() {
        return disciplinaRepository.findAll();
    }

    public Disciplina getDisciplina(Long id) {
        return disciplinaRepository.findById(id).orElse(null);
    }

    public Integer totalAlunos(Long disciplinaId) {
        return disciplinaRepository.findById(disciplinaId).get().getAlunos().size();
    }

    public Disciplina cadastrarDisciplina(Disciplina disciplina) {
        return disciplinaRepository.save(disciplina);
    }
}