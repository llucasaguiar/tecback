package br.uniesp.si.techback.dto;

import br.uniesp.si.techback.model.MetodoPagamento;
import br.uniesp.si.techback.model.Plano;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssinaturaDTO {

    private Long id;

    private String status; // Ex: ATIVA, INATIVA
    private LocalDateTime iniciadaEm;
    private LocalDate dataVencimento;
    private LocalDateTime canceladaEm;
    private Plano plano;
    private MetodoPagamento metodoPagamento;

}
