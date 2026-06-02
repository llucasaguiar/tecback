package br.uniesp.si.techback.client;

import br.uniesp.si.techback.dto.BrasilApiCepResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "brasilApiClient", url = "${brasilapi.url:https://brasilapi.com.br/api}")
public interface BrasilApiClient {

    // Rota da BrasilAPI para buscar CEP: https://brasilapi.com.br/api/cep/v1/{cep}
    @GetMapping("/cep/v1/{cep}")
    BrasilApiCepResponseDTO buscarPorCep(@PathVariable("cep") String cep);
}