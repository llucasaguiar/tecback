package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.FavoritoDTO;
import br.uniesp.si.techback.model.Favorito;
import br.uniesp.si.techback.service.FavoritoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/favoritos")
@RequiredArgsConstructor

public class FavoritoController {

    private final FavoritoService favoritoService;

    @GetMapping("/ordenando")
    public List<FavoritoDTO> listarFavoritos() {
        log.info("Listando todos os filmes favoritos");
        return favoritoService.listar();
    }

    @PostMapping
    public ResponseEntity<FavoritoDTO> salvar(@Valid @RequestBody FavoritoDTO favoritoDTO) {
        log.info("Recebida requisição para criar novo favorito: {}", favoritoDTO.getId());
        try {
            FavoritoDTO favoritoSalvo = favoritoService.salvar(favoritoDTO);
            log.info("Assinatura criada com sucesso. ID: {}", favoritoSalvo.getId());

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(favoritoSalvo.getId())
                    .toUri();
            log.debug("URI de localização do novo favorito: {}", location);

            return ResponseEntity.created(location).body(favoritoSalvo);
        } catch (Exception e) {
            log.error("Erro ao criar favorito: {}", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping
    public List<FavoritoDTO> listar() {
        log.info("Listando todos os favoritos");
        List<FavoritoDTO> favoritos = favoritoService.listar();
        log.debug("Total de favoritos encontrados: {}", favoritos.size());
        return favoritos;
    }

    @PutMapping("/{id}")
    public ResponseEntity<FavoritoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody FavoritoDTO favoritoDTO) {
        log.info("Atualizando favorito com ID {}: {}", id, favoritoDTO);
        try {
            FavoritoDTO favoritoAtualizado = favoritoService.atualizar(id, favoritoDTO);
            log.debug("Favorito ID {} atualizado com sucesso", id);
            return ResponseEntity.ok(favoritoAtualizado);
        } catch (Exception e) {
            log.error("Erro ao atualizar favorito ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        log.info("Excluindo favorito com ID: {}", id);
        try {
            favoritoService.excluir(id);
            log.debug("Favorito com ID {} excluído com sucesso", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Erro ao excluir favorito com ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }
}
