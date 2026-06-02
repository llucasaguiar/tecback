package br.uniesp.si.techback.controller;

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
    public ResponseEntity<Genero> salvar(@Valid @RequestBody Genero genero) {
        log.info("Recebida requisição para criar novo gênero: {}", genero.getNome());
        try {
            Genero generoSalvo = generoService.salvar(genero);
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
    public List<Genero> listar() {
        log.info("Listando todos os gêneros");
        List<Genero> generos = generoService.listar();
        log.debug("Total de gêneros encontrados: {}", generos.size());
        return generos;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genero> atualizar(@PathVariable Long id, @Valid @RequestBody Genero genero) {
        log.info("Atualizando gênero com ID {}: {}", id, genero);
        try {
            Genero generoAtualizado = generoService.atualizar(id, genero);
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
