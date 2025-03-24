package APSV.LabProjSoftware.services;

import APSV.LabProjSoftware.models.Aluno;

public interface SistemaDePagamento {
    void enviarCobranca(Aluno aluno, double valor);
  
  }
