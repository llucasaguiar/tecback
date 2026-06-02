package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.GeneroDTO;
import br.uniesp.si.techback.model.Genero;
import br.uniesp.si.techback.service.GeneroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/generos")
public class GeneroController {

    private final GeneroService generoService;

    private static List<Genero> lista = new ArrayList<>();

    @PostMapping
    public ResponseEntity<GeneroDTO> salvar(@Valid @RequestBody GeneroDTO generoDTO) {
        log.info("Recebida requisição para criar novo gênero: {}", generoDTO.getNome());
        try {
            GeneroDTO generoSalvo = generoService.salvar(generoDTO);
            log.info("Gênero criado com sucesso. ID: {}, Nome: {}", generoSalvo.getId(), generoSalvo.getNome());

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(generoSalvo.getId())
                    .toUri();
            log.debug("URI de localização do novo gênero: {}", location);

            return ResponseEntity.created(location).body(generoSalvo);
        } catch (Exception e) {
            log.error("Erro ao criar gênero: {}", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping
    public List<GeneroDTO> listar() {
        log.info("Listando todos os gêneros");
        List<GeneroDTO> generos = generoService.listar();
        log.debug("Total de gêneros encontrados: {}", generos.size());
        return generos;
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneroDTO> atualizar(@PathVariable Long id, @Valid @RequestBody GeneroDTO generoDTO) {
        log.info("Atualizando gênero com ID {}: {}", id, generoDTO);
        try {
            GeneroDTO generoAtualizado = generoService.atualizar(id, generoDTO);
            log.debug("Gênero ID {} atualizado com sucesso", id);
            return ResponseEntity.ok(generoAtualizado);
        } catch (Exception e) {
            log.error("Erro ao atualizar gênero ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        log.info("Excluindo gênero com ID: {}", id);
        try {
            generoService.excluir(id);
            log.debug("Gênero com ID {} excluído com sucesso", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Erro ao excluir gênero com ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }
}
