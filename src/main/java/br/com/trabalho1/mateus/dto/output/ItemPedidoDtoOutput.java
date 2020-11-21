package br.com.trabalho1.mateus.dto.output;

import br.com.trabalho1.mateus.entity.ItemPedido;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ItemPedidoDtoOutput {

    private String descricaoProduto;
    private Integer quantidade;

    public ItemPedidoDtoOutput(ItemPedido itemPedido){
        this.descricaoProduto = itemPedido.getProduto().getDescricao();
        this.quantidade = itemPedido.getQuantidade();
    }

    public static List<ItemPedidoDtoOutput> listFromItemPedido(List<ItemPedido> list){
        return list.stream().map(itemPedido -> new ItemPedidoDtoOutput(itemPedido)).collect(Collectors.toList());
    }
}
