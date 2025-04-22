package APSV.LabProjSoftware.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import APSV.LabProjSoftware.entities.Disciplina;
import APSV.LabProjSoftware.entities.Secretaria;
import APSV.LabProjSoftware.repositories.DisciplinaRepository;
import APSV.LabProjSoftware.repositories.SecretariaRepository;

@Service
public class SecretariaService {

    @Autowired
    private SecretariaRepository secretariaRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    public List<Secretaria> listarSecretarias() {
        return secretariaRepository.findAll();
    }

    public Map<String, Boolean> verificarSecretaria() {
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", secretariaRepository.findAll().size() > 0);
        return response;
    }

    public Secretaria cadastrarSecretaria(Secretaria secretaria) {
        return secretariaRepository.save(secretaria);
    }

    public void fecharPeriodoMatricula() {
        Secretaria secretaria = secretariaRepository.findAll().get(0);
        secretaria.fecharMatriculas();
        List<Disciplina> disciplinas = disciplinaRepository.findAll();
        List<Disciplina> disciplinasDesativadas = secretaria.fecharPeriodoMatricula(disciplinas);
        disciplinaRepository.saveAll(disciplinasDesativadas);
    }

    public void abrirPeriodoMatricula() {
        Secretaria secretaria = secretariaRepository.findAll().get(0);
        secretaria.abrirMatriculas();
        secretariaRepository.save(secretaria);
    }
}