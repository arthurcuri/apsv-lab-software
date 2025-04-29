package com.coinsystem.coinsystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coinsystem.coinsystem.models.Usuario;
import com.coinsystem.coinsystem.repositories.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepo;

    public Usuario login(String email) {
        return usuarioRepo.findAll().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst().orElseThrow();
    }
}
