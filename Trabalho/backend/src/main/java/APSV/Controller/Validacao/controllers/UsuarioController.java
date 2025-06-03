package APSV.Controller.Validacao.controllers;

import APSV.Controller.Validacao.dto.UsuarioCreateDTO;
import APSV.Controller.Validacao.dto.UsuarioDTO;
import APSV.Controller.Validacao.dto.LoginRequestDTO;
import APSV.Controller.Validacao.dto.RecuperarSenhaDTO;
import APSV.Controller.Validacao.dto.TransacaoResponseDTO;
import APSV.Controller.Validacao.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    // Listar alunos
    @GetMapping("/alunos")
    public ResponseEntity<List<Map<String, Object>>> listarAlunos() {
        return ResponseEntity.ok(
                usuarioService.listarPorTipo("ALUNO").stream()
                        .map(aluno -> {
                            Map<String, Object> map = new java.util.HashMap<>();
                            map.put("id", aluno.getId());
                            map.put("nome", aluno.getNome());
                            return map;
                        })
                        .toList());
    }

    // Buscar saldo de um professor (ou de qualquer usuário pelo ID)
    @GetMapping("/{id}/saldo")
    public ResponseEntity<Map<String, Integer>> getSaldo(@PathVariable Long id) {
        return usuarioService.buscarPorId(id)
                .map(usuario -> ResponseEntity.ok(Map.of("saldo", usuario.getMoedas())))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/distribuir-moedas")
    public ResponseEntity<?> distribuirMoedas(
            @PathVariable Long id, // ID do professor
            @RequestBody Map<String, Object> payload) {

        Long alunoId = Long.valueOf(payload.get("alunoId").toString());
        int quantidade = Integer.parseInt(payload.get("quantidade").toString());
        String motivo = payload.get("motivo").toString();

        try {
            usuarioService.distribuirMoedas(id, alunoId, quantidade, motivo);
            return ResponseEntity.ok(Map.of("mensagem", "Moedas distribuídas com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    @GetMapping("/{id}/historico")
    public ResponseEntity<List<TransacaoResponseDTO>> getHistorico(@PathVariable Long id) {
        List<TransacaoResponseDTO> historico = usuarioService.listarTransacoesPorOrigem(id);
        return ResponseEntity.ok(historico);
    }

    // Histórico de recebimentos (transações onde o usuário é DESTINO)
    @GetMapping("/{id}/historico-recebidos")
    public ResponseEntity<List<TransacaoResponseDTO>> getHistoricoRecebidos(@PathVariable Long id) {
        List<TransacaoResponseDTO> historico = usuarioService.listarTransacoesPorDestino(id);
        return ResponseEntity.ok(historico);
    }

    // Login de usuário
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO dto) {
        UsuarioDTO usuario = usuarioService.autenticar(dto.getEmail(), dto.getSenha());

        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }
    }

    // Recuperação de senha
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