package br.com.trabalho1.mateus.dto.output;

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

    private Integer quantidadeEstoque;

    private BigDecimal precoVenda;

    public ProdutoDtoOutput(Produto produto, Pessoa pessoa) {
        this.id = produto.getId();
        this.descricao = produto.getDescricao();
        this.quantidadeEstoque = produto.getQuantidadeEstoque();
        this.precoVenda = setPrecoVenda(produto, pessoa);
    }


    public BigDecimal setPrecoVenda(Produto produto, Pessoa pessoa) {
        String tipo = pessoa.toString();
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
