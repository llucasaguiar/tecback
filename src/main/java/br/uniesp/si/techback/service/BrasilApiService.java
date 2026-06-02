package br.uniesp.si.techback.service;

import br.uniesp.si.techback.client.BrasilApiClient;
import br.uniesp.si.techback.dto.BrasilApiCepResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BrasilApiService {

    @Autowired
    private BrasilApiClient brasilApiClient;

    public BrasilApiCepResponseDTO buscarEnderecoPorCep(String cep) {
        log.info("Iniciando consulta de CEP na BrasilAPI para o número: {}", cep);
        try {
            // Remove qualquer hífen ou espaço que o usuário possa ter digitado
            String cepFormatado = cep.replace("-", "").trim();

            BrasilApiCepResponseDTO response = brasilApiClient.buscarPorCep(cepFormatado);
            log.info("CEP {} consultado com sucesso na BrasilAPI.", cepFormatado);
            return response;
        } catch (Exception e) {
            log.error("Falha ao integrar com a BrasilAPI para o CEP {}: {}", cep, e.getMessage(), e);
            throw new RuntimeException("Erro ao consultar o CEP informado na base da BrasilAPI.");
        }
    }
}