package br.com.trabalho1.mateus.dto;


import lombok.*;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
public class UsuarioDtoInput {

    @NotNull(message = "Login não pode ser nulo")
    private String login;

    @NotNull(message = "Senha não pode ser nulo")
    private String senha;

    private Long idPessoa;

    @NotNull(message = "Informe se esse usuário vai ser um administrador ou não")
    private Boolean isAdministrador;

}
