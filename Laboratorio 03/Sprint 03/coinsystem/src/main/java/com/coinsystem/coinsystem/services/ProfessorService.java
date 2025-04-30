package com.coinsystem.coinsystem.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coinsystem.coinsystem.models.Aluno;
import com.coinsystem.coinsystem.models.Professor;
import com.coinsystem.coinsystem.models.Transacao   ;
import com.coinsystem.coinsystem.repositories.AlunoRepository;
import com.coinsystem.coinsystem.repositories.ProfessorRepository;
import com.coinsystem.coinsystem.repositories.TransacaoRepository;

@Service
public class ProfessorService {
    private final ProfessorRepository profRepo;
    private final AlunoRepository alunoRepo;
    private final TransacaoRepository txRepo;

    public ProfessorService(ProfessorRepository profRepo,
                            AlunoRepository alunoRepo,
                            TransacaoRepository txRepo) {
        this.profRepo = profRepo;
        this.alunoRepo = alunoRepo;
        this.txRepo = txRepo;
    }

    public Professor cadastrar(Professor prof) {
        prof.setMoedas(100);
        return profRepo.save(prof);
    }

    @Transactional
    public void distribuirMoedas(Long profId, Long alunoId, int qnt, String msg) {
        Professor prof = profRepo.findById(profId)
            .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
        Aluno aluno = alunoRepo.findById(alunoId)
            .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        if (prof.getMoedas() < qnt)
            throw new RuntimeException("Saldo insuficiente");

        prof.setMoedas(prof.getMoedas() - qnt);
        aluno.setMoedas(aluno.getMoedas() + qnt);
        profRepo.save(prof);
        alunoRepo.save(aluno);

        Transacao tx = new Transacao();
        tx.setData(LocalDateTime.now());
        tx.setQuantidade(qnt);
        tx.setTipo("DISTRIBUICAO");
        tx.setDescricao(msg);
        tx.setOrigem(prof);
        tx.setDestino(aluno);
        txRepo.save(tx);
    }

    public int consultarSaldo(Long profId) {
        return profRepo.findById(profId)
            .orElseThrow(() -> new RuntimeException("Professor não encontrado"))
            .getMoedas();
    }
}
