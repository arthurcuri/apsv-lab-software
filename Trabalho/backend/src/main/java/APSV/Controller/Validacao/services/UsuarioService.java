package APSV.Controller.Validacao.services;

import APSV.Controller.Validacao.dto.TransacaoResponseDTO;
import APSV.Controller.Validacao.dto.UsuarioCreateDTO;
import APSV.Controller.Validacao.dto.UsuarioDTO;
import APSV.Controller.Validacao.models.Usuario;
import APSV.Controller.Validacao.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TransacaoService transacaoService;

    // Criar usuário
    public UsuarioDTO criarUsuario(UsuarioCreateDTO dto) {
        Usuario usuario = converterParaEntity(dto);
        Usuario salvo = usuarioRepository.save(usuario);
        return converterParaDTO(salvo);
    }

    // Listar todos os usuários
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    // Buscar por ID
    public Optional<UsuarioDTO> buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(this::converterParaDTO);
    }

    // Atualizar usuário
    public UsuarioDTO atualizarUsuario(Long id, UsuarioCreateDTO dto) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNome(dto.getNome());
            usuario.setEmail(dto.getEmail());
            usuario.setSenha(dto.getSenha());
            usuario.setCpf(dto.getCpf());
            usuario.setCnpj(dto.getCnpj());
            usuario.setTipo(dto.getTipo() != null ? dto.getTipo() : usuario.getTipo());
            Usuario atualizado = usuarioRepository.save(usuario);
            return converterParaDTO(atualizado);
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    // Deletar usuário
    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // Conversões

    private UsuarioDTO converterParaDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCpf(),
                usuario.getCnpj(),
                usuario.getTipo(),
                usuario.getMoedas());
    }

    private Usuario converterParaEntity(UsuarioCreateDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        
        // Para empresas, sempre mocka o CPF
        if ("EMPRESA".equals(dto.getTipo())) {
            usuario.setCpf("12279768607");
        } else {
            usuario.setCpf(dto.getCpf());
        }
        
        usuario.setCnpj(dto.getCnpj());
        usuario.setTipo(dto.getTipo() != null ? dto.getTipo() : "ALUNO");
        
        // Define moedas padrão baseado no tipo
        if (dto.getMoedas() != null) {
            usuario.setMoedas(dto.getMoedas());
        } else {
            switch (usuario.getTipo()) {
                case "PROFESSOR":
                    usuario.setMoedas(1000);
                    break;
                case "EMPRESA":
                    usuario.setMoedas(0);
                    break;
                default: // ALUNO
                    usuario.setMoedas(0);
                    break;
            }
        }
        
        return usuario;
    }

    // Método de autenticação (login)
    public UsuarioDTO autenticar(String email, String senha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (usuario.getSenha().equals(senha)) {
                return new UsuarioDTO(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getEmail(),
                        usuario.getCpf(),
                        usuario.getCnpj(),
                        usuario.getTipo(),
                        usuario.getMoedas());
            }
        }

        return null;
    }

    public List<Usuario> listarPorTipo(String tipo) {
        return usuarioRepository.findByTipo(tipo);
    }

    public void distribuirMoedas(Long professorId, Long alunoId, int quantidade, String motivo) {
        Usuario professor = usuarioRepository.findById(professorId)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        Usuario aluno = usuarioRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        if (professor.getMoedas() < quantidade) {
            throw new RuntimeException("Saldo insuficiente");
        }

        professor.setMoedas(professor.getMoedas() - quantidade);
        aluno.setMoedas(aluno.getMoedas() + quantidade);

        usuarioRepository.save(professor);
        usuarioRepository.save(aluno);

        // Cria transação
        transacaoService.salvarTransacao(professor, aluno, quantidade, motivo);
    }

    public List<TransacaoResponseDTO> listarTransacoesPorOrigem(Long origemId) {
        return transacaoService.listarPorOrigem(origemId);
    }

public List<TransacaoResponseDTO> listarTransacoesPorDestino(Long destinoId) {
    // Busca as transações pelo destinoId usando o transacaoService
    return transacaoService.listarPorDestino(destinoId);
}

    // Método de recuperação de senha (simulado)
    public void recuperarSenha(String email) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (usuario.isPresent()) {
            System.out.println("Link de recuperação enviado para: " + email);
        } else {
            throw new RuntimeException("Email não encontrado");
        }
    }
}