package com.coinsystem.coinsystem.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime data;
    private int quantidade;

    // NOVOS CAMPOS
    private String tipo;        // ex: "DISTRIBUICAO" ou "RESGATE"
    private String descricao;   // mensagem ou nome da vantagem

    @ManyToOne
    private Usuario origem;

    @ManyToOne
    private Usuario destino;

    public Transacao() { }

    // construtor opcional incluindo origem/destino
    public Transacao(LocalDateTime data, int quantidade, String tipo,
                     String descricao, Usuario origem, Usuario destino) {
        this.data = data;
        this.quantidade = quantidade;
        this.tipo = tipo;
        this.descricao = descricao;
        this.origem = origem;
        this.destino = destino;
    }


    // getters e setters existentes...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    // GETTERS E SETTERS QUE FALTAVAM
    public String getTipo() { 
        return tipo; 
    }
    public void setTipo(String tipo) { 
        this.tipo = tipo; 
    }

    public String getDescricao() { 
        return descricao; 
    }
    public void setDescricao(String descricao) { 
        this.descricao = descricao; 
    }

    public Usuario getOrigem() {
        return origem;
    }

    public void setOrigem(Usuario origem) {
        this.origem = origem;
    }

    public Usuario getDestino() {
        return destino;
    }

    public void setDestino(Usuario destino) {
        this.destino = destino;
    }
}
