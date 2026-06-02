package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Slf4j
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> salvar(@Valid @RequestBody Usuario usuario) {
        log.info("Recebida requisição para criar novo usuário: {}", usuario.getNome());
        try {
            Usuario usuarioSalvo = usuarioService.salvar(usuario);
            log.info("Usuário criado com sucesso. ID: {}, Nome: {}", usuarioSalvo.getId(), usuarioSalvo.getNome());

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(usuarioSalvo.getId())
                    .toUri();
            log.debug("URI de localização do novo usuário: {}", location);

            return ResponseEntity.created(location).body(usuarioSalvo);
        } catch (Exception e) {
            log.error("Erro ao criar usuário: {}", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping
    public List<Usuario> listar() {
        log.info("Listando todos os usuários");
        List<Usuario> usuarios = usuarioService.listar();
        log.debug("Total de usuários encontrados: {}", usuarios.size());
        return usuarios;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        log.info("Atualizando usuário com ID {}: {}", id, usuario);
        try {
            Usuario usuarioAtualizado = usuarioService.atualizar(id, usuario);
            log.debug("Usuário ID {} atualizado com sucesso", id);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (Exception e) {
            log.error("Erro ao atualizar usuário ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        log.info("Excluindo usuário com ID: {}", id);
        try {
            usuarioService.excluir(id);
            log.debug("Usuário com ID {} excluído com sucesso", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Erro ao excluir usuário com ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }
}
