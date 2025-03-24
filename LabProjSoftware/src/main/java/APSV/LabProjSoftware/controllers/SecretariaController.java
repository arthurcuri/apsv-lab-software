package APSV.LabProjSoftware.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import APSV.LabProjSoftware.models.Disciplina;
import APSV.LabProjSoftware.models.Secretaria;
import APSV.LabProjSoftware.repositories.DisciplinaRepository;
import APSV.LabProjSoftware.repositories.SecretariaRepository;

@RestController
@RequestMapping("/secretaria")
public class SecretariaController {

  @Autowired
  private SecretariaRepository secretariaRepository;

  @Autowired
  private DisciplinaRepository disciplinaRepository;

  @GetMapping
  public List<Secretaria> listarSecretarias() {
    return secretariaRepository.findAll();
  }

  @GetMapping("/check")
  public Map<String, Boolean> verificarSecretaria() {
    Map<String, Boolean> response = new HashMap<>();
    response.put("exists", secretariaRepository.findAll().size() > 0);
    return response;
  }

  @PostMapping
  public Secretaria cadastrarSecretaria(@RequestBody Secretaria secretaria) {
    return secretariaRepository.save(secretaria);
  }

  @PostMapping("/fechar-matriculas")
  public void fecharPeriodoMatricula() {
    Secretaria secretaria = secretariaRepository.findAll().get(0);
    secretaria.fecharMatriculas();
    List<Disciplina> disciplinas = disciplinaRepository.findAll();
    List<Disciplina> disciplinasDesativadas = secretaria.fecharPeriodoMatricula(disciplinas);
    disciplinaRepository.saveAll(disciplinasDesativadas);
  }

  @PostMapping("/abrir-matriculas")
  public void abrirPeriodoMatricula() {
    Secretaria secretaria = secretariaRepository.findAll().get(0);
    secretaria.abrirMatriculas();
    secretariaRepository.save(secretaria);
  }

}
