package com.coinsystem.coinsystem.controllers;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coinsystem.coinsystem.models.EmpresaParceira;
import com.coinsystem.coinsystem.models.Vantagem;
import com.coinsystem.coinsystem.services.EmpresaParceiraService;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaParceiraController {
    private final EmpresaParceiraService svc;

    public EmpresaParceiraController(EmpresaParceiraService svc) {
        this.svc = svc;
    }

    @PostMapping
    public EmpresaParceira cadastrar(@RequestBody EmpresaParceira e) {
        return svc.cadastrar(e);
    }

    @PostMapping("/{empId}/vantagens")
    public Vantagem cadastrarVantagem(@PathVariable Long empId,
                                      @RequestBody Vantagem v) {
        return svc.cadastrarVantagem(empId, v);
    }
}
