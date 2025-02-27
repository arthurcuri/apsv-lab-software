package APSV.LabProjSoftware.services;

import APSV.LabProjSoftware.models.Matricula;
import APSV.LabProjSoftware.repositories.MatriculaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatriculaService {
    private final MatriculaRepository matriculaRepository;

    public MatriculaService(MatriculaRepository matriculaRepository) {
        this.matriculaRepository = matriculaRepository;
    }

    public List<Matricula> listarMatriculas() {
        return matriculaRepository.findAll();
    }
}
