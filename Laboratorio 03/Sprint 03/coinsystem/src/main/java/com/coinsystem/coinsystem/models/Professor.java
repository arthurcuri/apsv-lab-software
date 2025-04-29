package com.coinsystem.coinsystem.models;

import jakarta.persistence.Entity;

@Entity
public class Professor extends Usuario {

    private String cpf;
    private String departamento;

    public Professor() {
    }

    public Professor(String nome, String email, int moedas, String cpf, String senha, String departamento) {
        super(nome, email, senha, moedas);
        this.cpf = cpf;
        this.departamento = departamento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
}