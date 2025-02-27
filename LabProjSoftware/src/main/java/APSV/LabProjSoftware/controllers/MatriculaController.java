package APSV.LabProjSoftware.controllers;

import APSV.LabProjSoftware.models.Matricula;
import APSV.LabProjSoftware.services.MatriculaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/matriculas")
public class MatriculaController {
    private final MatriculaService matriculaService;

    public MatriculaController(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    @GetMapping
    public List<Matricula> listarMatriculas() {
        return matriculaService.listarMatriculas();
    }
}
