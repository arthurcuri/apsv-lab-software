package com.coinsystem.coinsystem.models;

import jakarta.persistence.Entity;

@Entity
public class Aluno extends Usuario {

    private String cpf;
    private String endereco;
    private String instituicaoEnsino;
    private String curso;

    public Aluno() {
    }

    public Aluno(String nome, String email, int moedas, String cpf, String endereco, String senha, String instituicaoEnsino, String curso) {
        super(nome, email, senha, moedas);
        this.cpf = cpf;
        this.endereco = endereco;
        this.instituicaoEnsino = instituicaoEnsino;
        this.curso = curso;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getInstituicaoEnsino() {
        return instituicaoEnsino;
    }

    public void setInstituicaoEnsino(String instituicaoEnsino) {
        this.instituicaoEnsino = instituicaoEnsino;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
}