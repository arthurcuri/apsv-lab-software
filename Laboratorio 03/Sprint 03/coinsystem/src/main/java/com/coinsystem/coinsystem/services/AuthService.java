package com.coinsystem.coinsystem.services;
// src/main/java/com/example/coinsystem/service/AuthService.java

import org.springframework.stereotype.Service;

import com.coinsystem.coinsystem.models.Usuario;
import com.coinsystem.coinsystem.repositories.UsuarioRepository;

@Service
public class AuthService {
    private final UsuarioRepository repo;
    public AuthService(UsuarioRepository repo) { this.repo = repo; }

    public Usuario login(String email, String senha) {
        return repo.findByEmailAndSenha(email, senha)
                   .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));
    }
}
