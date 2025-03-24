package APSV.LabProjSoftware.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import APSV.LabProjSoftware.models.Aluno;
import APSV.LabProjSoftware.repositories.AlunoRepository;
import APSV.LabProjSoftware.services.SistemaDePagamentoImpl;


@RestController
@RequestMapping("/pagamento")
public class PagamentoController {

  @Autowired
  private AlunoRepository alunoRepository;

  @Autowired
  private SistemaDePagamentoImpl sistemaDePagamento;

  @PostMapping("/realizar")
  public String enviarCobranca() {
    List<Aluno> alunos = alunoRepository.findAll();

    alunos.stream()
        .filter(a -> a.consultarDisciplinas() != null && !a.consultarDisciplinas().isEmpty())
        .forEach(aluno -> {
          int valor = aluno.consultarDisciplinas().size() * 400;
          sistemaDePagamento.enviarCobranca(aluno, valor);
        });

    return "boletos enviados";
  }

}
