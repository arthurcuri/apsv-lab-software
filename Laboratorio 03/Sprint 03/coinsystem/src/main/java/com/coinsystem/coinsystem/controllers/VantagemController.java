package com.coinsystem.coinsystem.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coinsystem.coinsystem.models.Vantagem;
import com.coinsystem.coinsystem.services.VantagemService;

@RestController
@RequestMapping("/api/vantagens")
public class VantagemController {
    private final VantagemService svc;

    public VantagemController(VantagemService svc) {
        this.svc = svc;
    }

    @GetMapping
    public List<Vantagem> listarTodas() {
        return svc.listarTodas();
    }
}
