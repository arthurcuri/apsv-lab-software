package APSV.LabProjSoftware.controllers;

import APSV.LabProjSoftware.entities.Aluno;
import APSV.LabProjSoftware.repositories.AlunoRepository;
import APSV.LabProjSoftware.services.SistemaDePagamentoImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final AlunoRepository alunoRepository;
    private final SistemaDePagamentoImpl sistemaDePagamento;

    public PagamentoController(AlunoRepository alunoRepository, SistemaDePagamentoImpl sistemaDePagamento) {
        this.alunoRepository = alunoRepository;
        this.sistemaDePagamento = sistemaDePagamento;
    }

    @PostMapping("/realizar")
    public ResponseEntity<List<String>> realizarCobranca() {
        List<Aluno> alunos = alunoRepository.findAll();
        List<String> cobrancas = sistemaDePagamento.gerarCobrancas(alunos);

        return cobrancas.isEmpty() 
            ? ResponseEntity.noContent().build() 
            : ResponseEntity.ok(cobrancas);
    }
}
