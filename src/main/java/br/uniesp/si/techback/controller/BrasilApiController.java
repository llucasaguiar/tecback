package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.BrasilApiCepResponseDTO;
import br.uniesp.si.techback.service.BrasilApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/brasil-api")
public class BrasilApiController {

    @Autowired
    private BrasilApiService brasilApiService;

    // Endpoint: GET /api/brasil-api/cep/{cep}
    @GetMapping("/cep/{cep}")
    public ResponseEntity<BrasilApiCepResponseDTO> consultarCep(@PathVariable String cep) {
        try {
            BrasilApiCepResponseDTO resultado = brasilApiService.buscarEnderecoPorCep(cep);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            log.error("Erro no endpoint de consulta de CEP: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}