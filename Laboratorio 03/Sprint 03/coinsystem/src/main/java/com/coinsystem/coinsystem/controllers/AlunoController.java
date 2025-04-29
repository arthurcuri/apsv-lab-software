package com.coinsystem.coinsystem.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coinsystem.coinsystem.models.Aluno;
import com.coinsystem.coinsystem.models.Extrato;
import com.coinsystem.coinsystem.services.AlunoService;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {
    private final AlunoService svc;

    public AlunoController(AlunoService svc) {
        this.svc = svc;
    }

    @PostMapping
    public Aluno cadastrar(@RequestBody Aluno a) {
        return svc.cadastrar(a);
    }

    @PostMapping("/{id}/resgatar/{vantId}")
    public void resgatar(@PathVariable Long id,
                        @PathVariable Long vantId) {
        svc.resgatarVantagem(id, vantId);
    }

    @GetMapping("/{id}/extrato")
    public Extrato extratoPorPeriodo(@PathVariable Long id,
                                     @RequestParam String inicio,
                                     @RequestParam String fim) {
        try {
            LocalDate di = LocalDate.parse(inicio);
            LocalDate df = LocalDate.parse(fim);
            return svc.gerarExtratoPeriodo(id, di, df);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data inválida. Use yyyy-MM-dd.");
        }
    }
}
