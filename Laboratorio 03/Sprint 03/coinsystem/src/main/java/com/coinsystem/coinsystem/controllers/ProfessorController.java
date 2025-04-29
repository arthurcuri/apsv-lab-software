package com.coinsystem.coinsystem.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coinsystem.coinsystem.models.Professor;
import com.coinsystem.coinsystem.services.ProfessorService;

@RestController
@RequestMapping("/api/professores")
public class ProfessorController {
    private final ProfessorService svc;

    public ProfessorController(ProfessorService svc) {
        this.svc = svc;
    }

    @PostMapping
    public Professor cadastrar(@RequestBody Professor p) {
        return svc.cadastrar(p);
    }

    @PostMapping("/{profId}/distribuir")
    public void distribuir(@PathVariable Long profId,
                           @RequestParam Long alunoId,
                           @RequestParam int quantidade,
                           @RequestParam String mensagem) {
        svc.distribuirMoedas(profId, alunoId, quantidade, mensagem);
    }

    @GetMapping("/{profId}/saldo")
    public int saldo(@PathVariable Long profId) {
        return svc.consultarSaldo(profId);
    }
}
