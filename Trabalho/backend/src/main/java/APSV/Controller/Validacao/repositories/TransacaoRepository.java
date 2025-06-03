package APSV.Controller.Validacao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import APSV.Controller.Validacao.models.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findByOrigemId(Long origemId);
    List<Transacao> findByDestinoId(Long destinoId);

}

