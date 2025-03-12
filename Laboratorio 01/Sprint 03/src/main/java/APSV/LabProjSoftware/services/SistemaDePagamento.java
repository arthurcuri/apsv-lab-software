package APSV.LabProjSoftware.services;

import APSV.LabProjSoftware.entities.Aluno;

public interface SistemaDePagamento {
  void enviarCobranca(Aluno aluno, double valor);

}
