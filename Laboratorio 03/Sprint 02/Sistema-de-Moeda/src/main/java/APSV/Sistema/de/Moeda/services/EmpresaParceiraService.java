package APSV.Sistema.de.Moeda.services;

import APSV.Sistema.de.Moeda.models.EmpresaParceira;
import APSV.Sistema.de.Moeda.repositories.EmpresaParceiraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaParceiraService {

    @Autowired
    private EmpresaParceiraRepository empresaParceiraRepository;

    // Listar todas as empresas parceiras
    public List<EmpresaParceira> listarTodas() {
        return empresaParceiraRepository.findAll();
    }

    // Buscar empresa parceira por ID
    public Optional<EmpresaParceira> buscarPorId(Long id) {
        return empresaParceiraRepository.findById(id);
    }

    // Criar nova empresa parceira
    public EmpresaParceira criar(EmpresaParceira empresaParceira) {
        return empresaParceiraRepository.save(empresaParceira);
    }

    // Atualizar empresa parceira existente
    public Optional<EmpresaParceira> atualizar(Long id, EmpresaParceira empresaAtualizada) {
        return empresaParceiraRepository.findById(id).map(empresa -> {
            empresa.setNome(empresaAtualizada.getNome());
            empresa.setCnpj(empresaAtualizada.getCnpj());
            // Atualize outros campos conforme necessário
            return empresaParceiraRepository.save(empresa);
        });
    }

    // Deletar empresa parceira
    public boolean deletar(Long id) {
        if (empresaParceiraRepository.existsById(id)) {
            empresaParceiraRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
