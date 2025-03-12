package APSV.LabProjSoftware.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import APSV.LabProjSoftware.entities.Aluno;

@Service
public class SistemaDePagamentoImpl implements SistemaDePagamento {

  @Autowired
  private EmailService emailService;

  @Override
  public void enviarCobranca(Aluno aluno, double valor) {
    System.out.println("boleto de R$" + valor + " enviado para o aluno: " + aluno.getNome());

    String assunto = "Boleto de Matrícula";
    String mensagem = "Olá " + aluno.getNome() + ", seu boleto de R$" + valor + " foi gerado!";
    String emailAluno = aluno.getEmail();

    emailService.enviarEmail(emailAluno, assunto, mensagem);
  }
}
