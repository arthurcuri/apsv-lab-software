package APSV.LabProjSoftware.services.impl;

import APSV.LabProjSoftware.interfaces.SistemaDePagamento;
import APSV.LabProjSoftware.models.Aluno;
import org.springframework.stereotype.Service;

@Service
public class SistemaDePagamentoImpl implements SistemaDePagamento {
    public void enviarCobranca(Aluno a, Double valor) {}
}
