package APSV.Controller.Validacao.services;

import APSV.Controller.Validacao.models.EmpresaParceira;
import APSV.Controller.Validacao.repositories.EmpresaParceiraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaParceiraService {

    @Autowired
    private EmpresaParceiraRepository repository;

    public List<EmpresaParceira> findAll() {
        return repository.findAll();
    }

    public Optional<EmpresaParceira> findById(Long id) {
        return repository.findById(id);
    }

    public EmpresaParceira save(EmpresaParceira empresaParceira) {
        return repository.save(empresaParceira);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}