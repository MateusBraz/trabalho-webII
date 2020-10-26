package br.com.trabalho1.mateus.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class PessoaDtoInput {

    private Long idResponsavel;

    private String tipo;

    @NotNull(message = "Situacao da pessoa não pode ser nulo")
    private String situacao;

    @NotNull(message = "Nome não pode ser nulo")
    private String nome;

    private String apelido;

    @NotNull(message = "Data de nascimento não pode ser nulo")
    private LocalDate dataNascimento;

    private String cpf;

    private String rg;

    private String cnpj;
}
