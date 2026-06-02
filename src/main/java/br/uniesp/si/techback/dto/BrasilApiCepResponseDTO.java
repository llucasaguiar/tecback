package br.uniesp.si.techback.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrasilApiCepResponseDTO {

    private String cep;
    private String state; // Corresponde ao 'uf'
    private String city;  // Corresponde à 'localidade'
    private String neighborhood; // Corresponde ao 'bairro'
    private String street;       // Corresponde ao 'logradouro'
    private String service;

    // A BrasilAPI não envia um booleano simples de erro no JSON padrão,
    // mas caso queira mapear respostas customizadas, podemos deixar a estrutura pronta.
}