package APSV.Controller.Validacao.controllers;

import APSV.Controller.Validacao.dto.UsuarioCreateDTO;
import APSV.Controller.Validacao.dto.UsuarioDTO;
import APSV.Controller.Validacao.dto.LoginRequestDTO;
import APSV.Controller.Validacao.dto.RecuperarSenhaDTO;
import APSV.Controller.Validacao.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Criar usuário
    @PostMapping
    public ResponseEntity<UsuarioDTO> criarUsuario(@RequestBody @Valid UsuarioCreateDTO dto) {
        UsuarioDTO novoUsuario = usuarioService.criarUsuario(dto);
        return ResponseEntity.ok(novoUsuario);
    }

    // Listar todos os usuários
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    // Buscar usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Atualizar usuário
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioCreateDTO dto) {
        try {
            UsuarioDTO atualizado = usuarioService.atualizarUsuario(id, dto);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Deletar usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    //  Login de usuário
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequestDTO dto) {
        boolean sucesso = usuarioService.autenticar(dto.getEmail(), dto.getSenha());
        return sucesso ? ResponseEntity.ok("Login realizado com sucesso")
                       : ResponseEntity.status(401).body("Credenciais inválidas");
    }

    //  Recuperação de senha
    @PostMapping("/recuperar-senha")
    public ResponseEntity<String> recuperarSenha(@RequestBody @Valid RecuperarSenhaDTO dto) {
        try {
            usuarioService.recuperarSenha(dto.getEmail());
            return ResponseEntity.ok("Link de recuperação enviado para o e-mail informado.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Email não encontrado.");
        }
    }
}