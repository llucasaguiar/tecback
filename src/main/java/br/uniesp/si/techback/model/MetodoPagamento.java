package br.uniesp.si.techback.model;

import br.uniesp.si.techback.validation.MetodoPagamentoValidation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "metodo_Pagamento")
public class MetodoPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @MetodoPagamentoValidation
    private String descricao; // Ex: Cartão de Crédito, Pix, Boleto

}
