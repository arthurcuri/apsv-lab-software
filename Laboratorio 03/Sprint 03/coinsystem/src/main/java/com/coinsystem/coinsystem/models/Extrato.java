package com.coinsystem.coinsystem.models;

import java.time.LocalDate;
import java.util.List;

public class Extrato {
    private List<Transacao> transacoes;
    private LocalDate dataInicio;
    private LocalDate dataFinal;

    public Extrato(List<Transacao> transacoes, LocalDate dataInicio, LocalDate dataFinal) {
        this.transacoes = transacoes;
        this.dataInicio = dataInicio;
        this.dataFinal = dataFinal;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(List<Transacao> transacoes) {
        this.transacoes = transacoes;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDate dataFinal) {
        this.dataFinal = dataFinal;
    }
}
