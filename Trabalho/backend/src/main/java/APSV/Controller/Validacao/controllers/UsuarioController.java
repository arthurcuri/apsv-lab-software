package APSV.Controller.Validacao.controllers;

import APSV.Controller.Validacao.dto.UsuarioCreateDTO;
import APSV.Controller.Validacao.dto.UsuarioDTO;
import APSV.Controller.Validacao.dto.LoginRequestDTO;
import APSV.Controller.Validacao.dto.RecuperarSenhaDTO;
import APSV.Controller.Validacao.dto.TransacaoResponseDTO;
import APSV.Controller.Validacao.dto.TransacaoDTO;
import APSV.Controller.Validacao.services.UsuarioService;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private Validator validator;

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

        try {
            // Criar TransacaoDTO a partir do payload
            TransacaoDTO transacaoDTO = new TransacaoDTO();
            transacaoDTO.setOrigemId(id); // Professor é a origem
            transacaoDTO.setDestinoId(Long.valueOf(payload.get("alunoId").toString()));
            
            // Tratar a quantidade de forma especial para permitir validação @Digits
            Object quantidadeObj = payload.get("quantidade");
            Integer quantidade = null;
            
            if (quantidadeObj != null) {
                try {
                    // Tentar converter para Double primeiro para detectar números fracionados
                    Double quantidadeDouble = Double.valueOf(quantidadeObj.toString());
                    
                    // Verificar se é um número inteiro
                    if (quantidadeDouble % 1 == 0) {
                        quantidade = quantidadeDouble.intValue();
                    } else {
                        // É um número fracionado - definir um valor que falhará na validação @Digits
                        // Vamos usar uma string que representa o número fracionado
                        transacaoDTO.setQuantidade(null); // Será tratado pela validação
                        
                        // Validar usando as anotações do TransacaoDTO
                        Set<ConstraintViolation<TransacaoDTO>> violations = validator.validate(transacaoDTO);
                        
                        // Adicionar violação customizada para número fracionado
                        return ResponseEntity.badRequest().body(Map.of("erro", "A quantidade de moedas deve ser um número inteiro"));
                    }
                } catch (NumberFormatException e) {
                    return ResponseEntity.badRequest().body(Map.of("erro", "Quantidade deve ser um número válido"));
                }
            }
            
            transacaoDTO.setQuantidade(quantidade);
            transacaoDTO.setMotivo(payload.get("motivo").toString());
            transacaoDTO.setData(LocalDateTime.now());

            // Validar usando as anotações do TransacaoDTO
            Set<ConstraintViolation<TransacaoDTO>> violations = validator.validate(transacaoDTO);
            
            if (!violations.isEmpty()) {
                String errorMessage = violations.iterator().next().getMessage();
                return ResponseEntity.badRequest().body(Map.of("erro", errorMessage));
            }

            usuarioService.distribuirMoedas(id, transacaoDTO.getDestinoId(), transacaoDTO.getQuantidade(), transacaoDTO.getMotivo());
            return ResponseEntity.ok(Map.of("mensagem", "Moedas distribuídas com sucesso"));
        } catch (Exception e) {
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