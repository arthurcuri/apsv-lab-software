package com.coinsystem.coinsystem.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coinsystem.coinsystem.models.Aluno;
import com.coinsystem.coinsystem.models.EmpresaParceira;
import com.coinsystem.coinsystem.models.Extrato;
import com.coinsystem.coinsystem.models.Transacao;
import com.coinsystem.coinsystem.models.Vantagem;
import com.coinsystem.coinsystem.repositories.AlunoRepository;
import com.coinsystem.coinsystem.repositories.EmpresaParceiraRepository;
import com.coinsystem.coinsystem.repositories.TransacaoRepository;
import com.coinsystem.coinsystem.repositories.VantagemRepository;

@Service
public class AlunoService {
    private final AlunoRepository alunoRepo;
    private final TransacaoRepository txRepo;
    private final VantagemRepository vantRepo;
    private final EmpresaParceiraRepository empRepo;

    public AlunoService(AlunoRepository alunoRepo,
                        TransacaoRepository txRepo,
                        VantagemRepository vantRepo,
                        EmpresaParceiraRepository empRepo) {
        this.alunoRepo = alunoRepo;
        this.txRepo = txRepo;
        this.vantRepo = vantRepo;
        this.empRepo = empRepo;
    }

    public List<Aluno> listarTodos() {
        return alunoRepo.findAll();
    }

    public Aluno cadastrar(Aluno aluno) {
        aluno.setMoedas(0);
        return alunoRepo.save(aluno);
    }

    @Transactional
    public void resgatarVantagem(Long alunoId, Long vantId) {
        Aluno aluno = alunoRepo.findById(alunoId)
            .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        Vantagem v = vantRepo.findById(vantId)
            .orElseThrow(() -> new RuntimeException("Vantagem não encontrada"));
        if (aluno.getMoedas() < v.getCusto())
            throw new RuntimeException("Saldo insuficiente");

        aluno.setMoedas(aluno.getMoedas() - v.getCusto());
        EmpresaParceira emp = v.getEmpresa();
        emp.setMoedas(emp.getMoedas() + v.getCusto());
        empRepo.save(emp);
        alunoRepo.save(aluno);

        Transacao tx = new Transacao();
        tx.setData(LocalDateTime.now());
        tx.setQuantidade(v.getCusto());
        tx.setTipo("RESGATE");
        tx.setDescricao(v.getNome());
        tx.setOrigem(aluno);
        tx.setDestino(emp);
        txRepo.save(tx);
    }

    public Extrato gerarExtratoPeriodo(Long alunoId, LocalDate inicio, LocalDate fim) {
        List<Transacao> todas = txRepo.findByOrigemIdOrDestinoId(alunoId, alunoId);
        List<Transacao> filtradas = todas.stream()
            .filter(tx -> {
                LocalDate d = tx.getData().toLocalDate();
                return !d.isBefore(inicio) && !d.isAfter(fim);
            })
            .collect(Collectors.toList());
        return new Extrato(filtradas, inicio, fim);
    }
}
