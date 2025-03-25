package APSV.LabProjSoftware.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import APSV.LabProjSoftware.entities.Aluno;

@Service
public class SistemaDePagamentoImpl implements SistemaDePagamento {

  @Override
    public String enviarCobranca(Aluno aluno, double valor) {
        return "Cobrança gerada para " + aluno.getNome() + ": R$" + valor;
    }

    public List<String> gerarCobrancas(List<Aluno> alunos) {
        return alunos.stream()
            .filter(a -> a.consultarDisciplinas() != null && !a.consultarDisciplinas().isEmpty())
            .map(aluno -> {
                double valor = aluno.consultarDisciplinas().size() * 400;
                return enviarCobranca(aluno, valor);
            })
            .collect(Collectors.toList());
    }
}
