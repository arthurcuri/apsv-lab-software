package com.coinsystem.coinsystem.controllers;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coinsystem.coinsystem.models.Usuario;
import com.coinsystem.coinsystem.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService svc;

    public AuthController(AuthService svc) {
        this.svc = svc;
    }

    @PostMapping("/login")
    public Usuario login(@RequestParam String email,
                         @RequestParam String senha) {
        return svc.login(email, senha);
    }
}
