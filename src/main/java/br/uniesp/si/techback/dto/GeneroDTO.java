package br.uniesp.si.techback.dto;

import br.uniesp.si.techback.model.Filme;
import br.uniesp.si.techback.validation.GeneroValidation;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneroDTO {

    private Long id;

    @NotBlank(message = "O nome é obrigatório.")
    @GeneroValidation
    private String nome;

    private String descricao;

    private List<Filme> filmes;

}
