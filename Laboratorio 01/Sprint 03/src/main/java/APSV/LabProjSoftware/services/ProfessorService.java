package APSV.LabProjSoftware.services;

import java.util.List;
import java.util.logging.Logger;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import APSV.LabProjSoftware.entities.Disciplina;
import APSV.LabProjSoftware.entities.Professor;
import APSV.LabProjSoftware.repositories.DisciplinaRepository;
import APSV.LabProjSoftware.repositories.ProfessorRepository;

@Service
public class ProfessorService {

    private static final Logger LOGGER = Logger.getLogger(ProfessorService.class.getName());

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    public List<Professor> listarProfessores() {
        return professorRepository.findAll();
    }

    public List<Disciplina> listarDisciplinas(Long professorId) {
        Professor professor = professorRepository.findById(professorId)
            .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
        return professor.getDisciplinas();
    }

    public List<Disciplina> getDisciplinasByProfessorId(Long professorId) {
        LOGGER.info("Buscando disciplinas para o professor com ID: " + professorId);

        Professor professor = professorRepository.findById(professorId)
            .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        List<Disciplina> disciplinas = professor.getDisciplinas();
        LOGGER.info("Disciplinas encontradas: " + disciplinas.size());

        List<Disciplina> disciplinasCompletas = new ArrayList<>();
        for (Disciplina d : disciplinas) {
            disciplinaRepository.findById(d.getId()).ifPresent(disciplinasCompletas::add);
        }

        return disciplinasCompletas;
    }

    public Professor cadastrarProfessor(Professor professor) {
        return professorRepository.save(professor);
    }

    public List<Professor> cadastrarProfessores(List<Professor> professores) {
        return professorRepository.saveAll(professores);
    }

    public Professor adicionarDisciplina(Long professorId, Long disciplinaId) {
        LOGGER.info("Adicionando disciplina ID: " + disciplinaId + " ao professor ID: " + professorId);

        Professor professor = professorRepository.findById(professorId)
            .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
            .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));

        professor.addDisciplina(disciplina);
        disciplina.setProfessor(professor);

        disciplinaRepository.save(disciplina);
        professor = professorRepository.save(professor);

        return professorRepository.findById(professor.getId())
            .orElseThrow(() -> new RuntimeException("Erro ao recuperar professor após atualização"));
    }

    public Professor removerDisciplina(Long professorId, Long disciplinaId) {
        LOGGER.info("Removendo disciplina ID: " + disciplinaId + " do professor ID: " + professorId);

        Professor professor = professorRepository.findById(professorId)
            .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
            .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));

        professor.removeDisciplina(disciplina);
        disciplina.setProfessor(null);

        disciplinaRepository.save(disciplina);
        professor = professorRepository.save(professor);

        return professorRepository.findById(professor.getId())
            .orElseThrow(() -> new RuntimeException("Erro ao recuperar professor após atualização"));
    }
}
