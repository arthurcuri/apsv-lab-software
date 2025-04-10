package APSV.Sistema.de.Moeda.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import APSV.Sistema.de.Moeda.models.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    // Implementação de métodos específicos para Aluno, se necessário
    
}
