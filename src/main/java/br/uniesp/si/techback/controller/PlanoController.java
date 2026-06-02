package br.uniesp.si.techback.controller;


import br.uniesp.si.techback.dto.PlanoDTO;
import br.uniesp.si.techback.model.Plano;
import br.uniesp.si.techback.service.PlanoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/planos")
@RequiredArgsConstructor
@Slf4j
public class PlanoController {

    private final PlanoService planoService;

    @PostMapping
    public ResponseEntity<PlanoDTO> salvar(@Valid @RequestBody PlanoDTO planoDTO) {
        log.info("Recebida requisição para criar novo plano: {}", planoDTO.getNome());
        try {
            PlanoDTO planoSalvo = planoService.salvar(planoDTO);
            log.info("Plano criado com sucesso. ID: {}, Nome: {}", planoSalvo.getId(), planoSalvo.getNome());

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(planoSalvo.getId())
                    .toUri();
            log.debug("URI de localização do novo plano: {}", location);

            return ResponseEntity.created(location).body(planoSalvo);
        } catch (Exception e) {
            log.error("Erro ao criar plano: {}", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping
    public List<PlanoDTO> listar() {
        log.info("Listando todos os planos");
        List<PlanoDTO> planos = planoService.listar();
        log.debug("Total de planos encontrados: {}", planos.size());
        return planos;
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody PlanoDTO planoDTO) {
        log.info("Atualizando plano com ID {}: {}", id, planoDTO);
        try {
            PlanoDTO planoAtualizado = planoService.atualizar(id, planoDTO);
            log.debug("Plano ID {} atualizado com sucesso", id);
            return ResponseEntity.ok(planoAtualizado);
        } catch (Exception e) {
            log.error("Erro ao atualizar plano ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        log.info("Excluindo plano com ID: {}", id);
        try {
            planoService.excluir(id);
            log.debug("Plano com ID {} excluído com sucesso", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Erro ao excluir plano com ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }
}
