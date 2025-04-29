package com.coinsystem.coinsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coinsystem.coinsystem.models.Usuario;
import com.coinsystem.coinsystem.services.UsuarioService;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> login(@RequestParam String email) {
        return ResponseEntity.ok(usuarioService.login(email));
    }
}
