package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.AssinaturaDTO;
import br.uniesp.si.techback.model.Assinatura;
import br.uniesp.si.techback.service.AssinaturaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/assinaturas")
@RequiredArgsConstructor
@Slf4j
public class AssinaturaController {

    private final AssinaturaService assinaturaService;

    @PostMapping
    public ResponseEntity<AssinaturaDTO> salvar(@Valid @RequestBody AssinaturaDTO assinaturaDTO) {
        log.info("Recebida requisição para criar nova assinatura: {}", assinaturaDTO.getId());
        try {
            AssinaturaDTO assinaturaSalva = assinaturaService.salvar(assinaturaDTO);
            log.info("Assinatura criada com sucesso. ID: {}", assinaturaSalva.getId());

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(assinaturaSalva.getId())
                    .toUri();
            log.debug("URI de localização da nova assinatura: {}", location);

            return ResponseEntity.created(location).body(assinaturaSalva);
        } catch (Exception e) {
            log.error("Erro ao criar assinatura: {}", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping
    public List<AssinaturaDTO> listar() {
        log.info("Listando todas as assinaturas");
        List<AssinaturaDTO> assinaturas = assinaturaService.listar();
        log.debug("Total de assinaturas encontradas: {}", assinaturas.size());
        return assinaturas;
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssinaturaDTO> atualizar(@PathVariable Long id, @Valid @RequestBody AssinaturaDTO assinaturaDTO) {
        log.info("Atualizando assinatura com ID {}: {}", id, assinaturaDTO);
        try {
            AssinaturaDTO assinaturaAtualizada = assinaturaService.atualizar(id, assinaturaDTO);
            log.debug("Assinatura ID {} atualizada com sucesso", id);
            return ResponseEntity.ok(assinaturaAtualizada);
        } catch (Exception e) {
            log.error("Erro ao atualizar assinatura ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        log.info("Excluindo assinatura com ID: {}", id);
        try {
            assinaturaService.excluir(id);
            log.debug("Assinatura com ID {} excluída com sucesso", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Erro ao excluir assinatura com ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }
}
