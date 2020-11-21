package br.com.trabalho1.mateus.dto.input;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class PedidoDtoInput {

    @NotNull(message = "Id da pessoa não pode ser nulo")
    private Long idPessoa;

    @NotNull(message = "Item pedidos não pode ser nulo")
    private List<ItemPedidoDtoInput> itemPedidoList;

    private String status;

    private LocalDate dataEntrega;

    private BigDecimal percentualDesconto;

}
