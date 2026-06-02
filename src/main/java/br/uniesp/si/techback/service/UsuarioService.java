package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.UsuarioDTO;
import br.uniesp.si.techback.mapper.UsuarioMapper;
import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Transactional
    public UsuarioDTO salvar(UsuarioDTO usuarioDTO) {
        log.info("Salvando novo usuário: {}", usuarioDTO.getId());
        try {
            Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
            Usuario usuarioSalvo = usuarioRepository.save(usuario);
            log.info("Usuário salvo com sucesso. ID: {}", usuarioSalvo.getId());
            return usuarioMapper.toDTO(usuarioSalvo);
        } catch (Exception e) {
            log.error("Falha ao salvar usuário '{}': {}", usuarioDTO.getId(), e.getMessage(), e);
            throw e;
        }
    }

    public List<UsuarioDTO> listar() {
        log.info("Buscando todos os usuários cadastrados");
        try {
            List<Usuario> usuarios = usuarioRepository.findAll();
            List<UsuarioDTO> usuariosDTO = usuarios.stream()
                    .map(usuarioMapper::toDTO)
                    .collect(Collectors.toList());
            log.debug("Total de usuários encontrados: {}", usuariosDTO.size());
            return usuariosDTO;
        } catch (Exception e) {
            log.error("Falha ao buscar usuários: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public UsuarioDTO atualizar(Long id, UsuarioDTO usuarioDTO) {
        log.info("Atualizando usuário ID: {}", id);
        Usuario usuarioAtualizado = usuarioRepository.findById(id)
                .map(usuarioExistente -> {
                    log.debug("Dados atuais do usuário: {}", usuarioExistente);
                    log.debug("Novos dados: {}", usuarioDTO);
                    usuarioDTO.setId(id);
                    Usuario usuarioParaAtualizar = usuarioMapper.toEntity(usuarioDTO);
                    Usuario usuarioSalvo = usuarioRepository.save(usuarioParaAtualizar);
                    log.info("Usuário ID: {} atualizado com sucesso. Novo usuário: {}",
                            id, usuarioSalvo.getId());
                    return usuarioSalvo;
                })
                .orElseThrow(() -> {
                    String mensagem = String.format("Falha ao atualizar: usuário não encontrado com o ID: %s", id.toString());
                    log.warn(mensagem);
                    return new RuntimeException(mensagem);
                });
        return usuarioMapper.toDTO(usuarioAtualizado);
    }

    @Transactional
    public void excluir(Long id) {
        log.info("Excluindo usuario ID: {}", id);
        if (!usuarioRepository.existsById(id)) {
            String mensagem = String.format("Falha ao excluir: usuário não encontrado com o ID: %d", id);
            log.warn(mensagem);
            throw new RuntimeException(mensagem);
        }
        try {
            usuarioRepository.deleteById(id);
            log.info("Usuário ID: {} excluído com sucesso", id);
        } catch (Exception e) {
            log.error("Erro ao excluir usuário ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}
