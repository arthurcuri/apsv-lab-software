package com.coinsystem.coinsystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coinsystem.coinsystem.models.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findByOrigemIdOrDestinoId(Long origemId, Long destinoId);
}