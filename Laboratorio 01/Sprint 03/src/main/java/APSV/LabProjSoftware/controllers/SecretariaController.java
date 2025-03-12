package APSV.LabProjSoftware.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import APSV.LabProjSoftware.entities.Secretaria;
import APSV.LabProjSoftware.services.SecretariaService;

@RestController
@RequestMapping("/secretaria")
public class SecretariaController {

    @Autowired
    private SecretariaService secretariaService;

    @GetMapping
    public List<Secretaria> listarSecretarias() {
        return secretariaService.listarSecretarias();
    }

    @GetMapping("/check")
    public Map<String, Boolean> verificarSecretaria() {
        return secretariaService.verificarSecretaria();
    }

    @PostMapping
    public Secretaria cadastrarSecretaria(@RequestBody Secretaria secretaria) {
        return secretariaService.cadastrarSecretaria(secretaria);
    }

    @PostMapping("/fechar-matriculas")
    public void fecharPeriodoMatricula() {
        secretariaService.fecharPeriodoMatricula();
    }

    @PostMapping("/abrir-matriculas")
    public void abrirPeriodoMatricula() {
        secretariaService.abrirPeriodoMatricula();
    }
}