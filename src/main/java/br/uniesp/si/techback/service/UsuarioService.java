package br.uniesp.si.techback.service;

import br.uniesp.si.techback.mapper.UsuarioMapper;
import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {

    private final UsuarioRepository usuariosRepository;
    private final UsuarioMapper usuarioMapper;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        log.info("Salvando novo usuário: {}", usuario.getId());
        try {
            Usuario usuarioSalvo = usuariosRepository.save(usuario);
            log.info("Usuário salvo com sucesso. ID: {}, Nome: {}", usuarioSalvo.getId(), usuarioSalvo.getNome());
            return usuarioSalvo;
        } catch (Exception e) {
            log.error("Falha ao salvar usuário '{}': {}", usuario.getId(), e.getMessage(), e);
            throw e;
        }
    }

    public List<Usuario> listar() {
        log.info("Buscando todos os usuários cadastrados");
        try {
            List<Usuario> usuarios = usuariosRepository.findAll();
            log.debug("Total de usuários encontrados: {}", usuarios.size());
            return usuarios;
        } catch (Exception e) {
            log.error("Falha ao buscar usuários: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public Usuario atualizar(Long id, Usuario usuario) {
        log.info("Atualizando usuário ID: {}", id);
        return usuariosRepository.findById(id)
                .map(usuarioExistente -> {
                    log.debug("Dados atuais de usuário: {}", usuarioExistente);
                    log.debug("Novos dados: {}", usuario);
                    usuario.setId(id);
                    Usuario usuarioAtualizado = usuariosRepository.save(usuario);
                    log.info("Usuário ID: {} atualizado com sucesso. Novo usuário: {}",
                            id, usuarioAtualizado.getId());
                    return usuarioAtualizado;
                })
                .orElseThrow(() -> {
                    String mensagem = String.format("Falha ao atualizar: usuário não encontrado com o ID: %d", id);
                    log.warn(mensagem);
                    return new RuntimeException(mensagem);
                });
    }

    @Transactional
    public void excluir(Long id) {
        log.info("Excluindo usuario ID: {}", id);
        if (!usuariosRepository.existsById(id)) {
            String mensagem = String.format("Falha ao excluir: usuário não encontrado com o ID: %d", id);
            log.warn(mensagem);
            throw new RuntimeException(mensagem);
        }
        try {
            usuariosRepository.deleteById(id);
            log.info("Usuário ID: {} excluído com sucesso", id);
        } catch (Exception e) {
            log.error("Erro ao excluir usuário ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}
