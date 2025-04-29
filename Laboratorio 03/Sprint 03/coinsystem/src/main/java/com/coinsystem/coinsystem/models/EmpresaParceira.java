package com.coinsystem.coinsystem.models;

import jakarta.persistence.Entity;

// EmpresaParceira.java
@Entity
public class EmpresaParceira extends Usuario {

    private String cnpj;

    public EmpresaParceira() {
    }

    public EmpresaParceira(String nome, String email, int moedas, String senha, String cnpj) {
        super(nome, email, senha, moedas);
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}