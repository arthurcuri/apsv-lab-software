package APSV.Controller.Validacao.services;

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

    // Criar usuário
    public UsuarioDTO criarUsuario(UsuarioCreateDTO dto) {
        Usuario usuario = converterParaEntity(dto);
        usuario.setMoedas(0); // Inicializa moedas
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
            Usuario atualizado = usuarioRepository.save(usuario);
            return converterParaDTO(atualizado);
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    // Deletar usuário
    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // ✅ Conversões

    private UsuarioDTO converterParaDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCpf(),
                usuario.getMoedas()
        );
    }

    private Usuario converterParaEntity(UsuarioCreateDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setCpf(dto.getCpf());
        return usuario;
    }
}


