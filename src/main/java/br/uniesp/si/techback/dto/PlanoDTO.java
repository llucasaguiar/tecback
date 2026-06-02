package br.uniesp.si.techback.dto;

import br.uniesp.si.techback.model.Assinatura;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanoDTO {

    private Long id;

    @NotBlank(message = "O nome é obrigatório.")
    private String nome; // Ex: Básico, Padrão, Premium

    private Double preco;
    private int limiteDiario;
    private int streamsSimultaneos;
    private List<Assinatura> assinaturas;
}
