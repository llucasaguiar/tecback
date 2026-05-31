package br.uniesp.si.techback.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "planos")
public class Plano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome; // Ex: Básico, Padrão, Premium

    private Double preco;
    private int limiteDiario;
    private int streamsSimultaneos;

    @OneToMany(mappedBy = "plano", cascade = CascadeType.ALL)
    private List<Assinatura> assinaturas;

}
