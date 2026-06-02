package br.uniesp.si.techback.dto;

import br.uniesp.si.techback.model.Filme;
import br.uniesp.si.techback.model.Usuario;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoritoDTO {

    private Long id;
    private Usuario usuario;
    private Filme filme;

    @NotBlank(message = "A data de criação é obrigatória.")
    private LocalDateTime criadoEm;

}
