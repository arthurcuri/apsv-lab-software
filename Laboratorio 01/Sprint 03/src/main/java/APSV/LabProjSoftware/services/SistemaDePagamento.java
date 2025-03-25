package APSV.LabProjSoftware.services;

import APSV.LabProjSoftware.entities.Aluno;

public interface SistemaDePagamento {
  String enviarCobranca(Aluno aluno, double valor);

}
