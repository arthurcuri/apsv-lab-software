package APSV.Sistema.de.Moeda.services;

import APSV.Sistema.de.Moeda.models.Aluno;
import APSV.Sistema.de.Moeda.repositories.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    // Listar todos os alunos
    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    // Buscar aluno por ID
    public Optional<Aluno> buscarPorId(Long id) {
        return alunoRepository.findById(id);
    }

    // Criar novo aluno
    public Aluno criar(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    // Atualizar aluno existente
    public Optional<Aluno> atualizar(Long id, Aluno alunoAtualizado) {
        return alunoRepository.findById(id).map(aluno -> {
            aluno.setNome(alunoAtualizado.getNome());
            aluno.setEmail(alunoAtualizado.getEmail());
            // Atualize outros campos conforme necessário
            return alunoRepository.save(aluno);
        });
    }

    // Deletar aluno
    public boolean deletar(Long id) {
        if (alunoRepository.existsById(id)) {
            alunoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
