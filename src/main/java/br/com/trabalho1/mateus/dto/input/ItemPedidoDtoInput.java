package br.com.trabalho1.mateus.dto.input;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ItemPedidoDtoInput {

    @NotNull(message = "Id do produto não pode ser nulo")
    private Long idProduto;

    private Integer quantidade;

    private Long idPedido;

}
