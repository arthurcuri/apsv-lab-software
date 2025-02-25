package APSV.LabProjSoftware.interfaces;

import APSV.LabProjSoftware.models.Aluno;

public interface SistemaDePagamento {
    void enviarCobranca(Aluno a, Double valor);
}
