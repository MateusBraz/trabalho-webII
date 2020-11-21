package br.com.trabalho1.mateus.dto.output;

import br.com.trabalho1.mateus.entity.Pedido;
import br.com.trabalho1.mateus.enuns.EStatusPedido;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PedidoDtoOutput {

    private Long id;
    private String pessoaNome;
    private EStatusPedido status;
    private LocalDate dataCompra;
    private LocalDate dataEntrega;
    private BigDecimal percentualDesconto;
    private List<ItemPedidoDtoOutput> itemPedidoList;

    public PedidoDtoOutput(Pedido pedido) {
        this.id = pedido.getId();
        this.pessoaNome = pedido.getPessoa().getNome();
        this.status = pedido.getStatus();
        this.dataCompra = pedido.getDataCompra();
        this.dataEntrega = pedido.getDataEntrega();
        this.percentualDesconto = pedido.getPercentualDesconto();
        this.itemPedidoList = ItemPedidoDtoOutput.listFromItemPedido(pedido.getItemPedidoList());
    }

    public static List<PedidoDtoOutput> listFromPedido(List<Pedido> list) {
        return list.stream().map(pedido -> new PedidoDtoOutput(pedido)).collect(Collectors.toList());
    }

}
