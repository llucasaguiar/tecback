package br.uniesp.si.techback.dto;

import br.uniesp.si.techback.validation.GeneroValidation;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilmeDTO {

    private Long id;

    @NotBlank(message = "O título é obrigatório.")
    private String titulo;

    private String sinopse;

    @NotBlank(message = "A data d lançamento é obrigatória.")
    private LocalDate dataLancamento;

    @GeneroValidation
    private String genero;

    private Integer duracaoMinutos;

    private String classificacaoIndicativa;
}
