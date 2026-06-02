package br.uniesp.si.techback.dto;

import br.uniesp.si.techback.model.Assinatura;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private Long id;

    @NotBlank(message = "O CPF_CNPJ é obrigatório.")
    private String cpf_cnpj;

    @NotBlank(message = "O e-mail é obrigatório.")
    private String email;

    @NotBlank(message = "A senha Hash é obrigatória.")
    private String senhaHash;

    private String nome;
    private Date dataNascimento;
    private Assinatura assinatura;
}
