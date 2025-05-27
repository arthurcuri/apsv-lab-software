package APSV.Controller.Validacao.repositories;

import APSV.Controller.Validacao.models.Vantagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VantagemRepository extends JpaRepository<Vantagem, Long> {
}

