package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.MetodoPagamentoDTO;
import br.uniesp.si.techback.model.MetodoPagamento;
import br.uniesp.si.techback.service.MetodoPagamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/metodoPagamento")
@RequiredArgsConstructor
@Slf4j
public class MetodoPagamentoContoller {

    private final MetodoPagamentoService metodoPagamentoService;

    @PostMapping
    public ResponseEntity<MetodoPagamentoDTO> salvar(@Valid @RequestBody MetodoPagamentoDTO metodoPagamentoDTO) {
        log.info("Recebida requisição para criar novo método de pagamento: {}", metodoPagamentoDTO.getDescricao());
        try {
            MetodoPagamentoDTO metodoPagamentoSalvo = metodoPagamentoService.salvar(metodoPagamentoDTO);
            log.info("Método de pagamento criado com sucesso. ID: {}, Descrição: {}", metodoPagamentoSalvo.getId(), metodoPagamentoSalvo.getDescricao());

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(metodoPagamentoSalvo.getId())
                    .toUri();
            log.debug("URI de localização do novo método de pagamento: {}", location);

            return ResponseEntity.created(location).body(metodoPagamentoSalvo);
        } catch (Exception e) {
            log.error("Erro ao criar método de pagamento: {}", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping
    public List<MetodoPagamentoDTO> listar() {
        log.info("Listando todos os métodos de pagamento");
        List<MetodoPagamentoDTO> metodoPagamentos = metodoPagamentoService.listar();
        log.debug("Total de métodos de pagamento encontrados: {}", metodoPagamentos.size());
        return metodoPagamentos;
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetodoPagamentoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody MetodoPagamentoDTO metodoPagamentoDTO) {
        log.info("Atualizando método de pagamento com ID {}: {}", id, metodoPagamentoDTO);
        try {
            MetodoPagamentoDTO metodoPagamentoAtualizado = metodoPagamentoService.atualizar(id, metodoPagamentoDTO);
            log.debug("Método de pagamento ID {} atualizado com sucesso", id);
            return ResponseEntity.ok(metodoPagamentoAtualizado);
        } catch (Exception e) {
            log.error("Erro ao atualizar método de pagamento ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        log.info("Excluindo método de pagamento com ID: {}", id);
        try {
            metodoPagamentoService.excluir(id);
            log.debug("Método de pagamento com ID {} excluído com sucesso", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Erro ao excluir método de pagamento com ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }
}
