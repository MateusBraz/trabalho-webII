package br.com.trabalho1.mateus.dto;

import br.com.trabalho1.mateus.entity.Pessoa;
import br.com.trabalho1.mateus.entity.Produto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProdutoDtoOutput {

    private Long id;

    private String descricao;

    private Long quantidadeEstoque;

    private BigDecimal precoVenda;

    public ProdutoDtoOutput(Produto produto, Pessoa pessoa) {
        this.id = produto.getId();
        this.descricao = produto.getDescricao();
        this.quantidadeEstoque = produto.getQuantidadeEstoque();
        this.precoVenda = getPrecoVenda(produto, pessoa);
    }


    public BigDecimal getPrecoVenda(Produto produto, Pessoa pessoa) {
        String tipo = pessoa.toString().substring(0, pessoa.toString().length() - 2).toUpperCase();
        if (tipo.equalsIgnoreCase("FISICA")) {
            return produto.getPrecoVendaFisica();
        } else {
            return produto.getPrecoVendaJuridica();
        }
    }

    public static List<ProdutoDtoOutput> listFromProduto(List<Produto> produtos, Pessoa pessoa) {
        final List<ProdutoDtoOutput> collect = produtos.stream().map((Produto produto) -> new ProdutoDtoOutput(produto, pessoa)).collect(Collectors.toList());
        return collect;
    }
}
