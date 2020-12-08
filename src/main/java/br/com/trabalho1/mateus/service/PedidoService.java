package br.com.trabalho1.mateus.service;

import br.com.trabalho1.mateus.dto.input.PedidoDtoInput;
import br.com.trabalho1.mateus.entity.*;
import br.com.trabalho1.mateus.enuns.EStatusPedido;
import br.com.trabalho1.mateus.repository.PedidoRepository;
import br.com.trabalho1.mateus.repository.ProdutoRepository;
import br.com.trabalho1.mateus.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    AutenticacaoService autenticacaoService;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    PessoaService pessoaService;

    @Autowired
    ProdutoService produtoService;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public List<Pedido> buscarTodos() {
        return pedidoRepository.findAll();
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto de id " + id + " não encontrado na base de dados"));
    }

    public Pedido inserir(String login, String senha, PedidoDtoInput pedidoDtoInput) {
        autenticacaoService.validarUsuarioAdministrador(login, senha);
        validar(pedidoDtoInput);
        Usuario usuario = usuarioRepository.findByLoginAndSenha(login, senha).orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        List<Produto> listProdutoAtualizar = new ArrayList<>();

        Pedido pedido = new Pedido();
        pedido.setPessoa(usuario.getPessoa());
        pedido.setStatus(EStatusPedido.REALIZADO);
        pedido.setDataCompra(LocalDate.now());
        pedido.setDataEntrega(pedidoDtoInput.getDataEntrega());
        pedido.setPercentualDesconto(pedidoDtoInput.getPercentualDesconto());

        List<ItemPedido> listItemPedido = pedidoDtoInput.getItemPedidoList()
                .stream()
                .map(itemPedidoDtoInput -> {
                    Produto produto = produtoService.buscarPorIdProduto(itemPedidoDtoInput.getIdProduto());
                    if (itemPedidoDtoInput.getQuantidade() > produto.getQuantidadeEstoque()) {
                        throw new RuntimeException("Quantidade no estoque do produto " + produto.getDescricao() + " inferior a quantidade do pedido");
                    }
                    ItemPedido itemPedido = ItemPedido.itemPedidoBuilder()
                            .pedido(pedido)
                            .produto(produto)
                            .quantidade(itemPedidoDtoInput.getQuantidade())
                            .build();
                    produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - itemPedidoDtoInput.getQuantidade());
                    listProdutoAtualizar.add(produto);
                    return itemPedido;
                })
                .collect(Collectors.toList());

        pedido.setItemPedidoList(listItemPedido);
        produtoRepository.saveAll(listProdutoAtualizar);
        return pedidoRepository.save(pedido);
    }

    public Pedido cancelar(Long id, String login, String senha) {
        autenticacaoService.validarUsuarioAdministrador(login, senha);
        Pedido pedido = buscarPorId(id);
        if(pedido.getStatus().equals(EStatusPedido.CANCELADO)){
            throw new RuntimeException("Pedido já se encontra cancelado");
        }
        List<ItemPedido> listItemPedido = pedido.getItemPedidoList();
        List<Produto> listProdutoAtualizar = new ArrayList<>();
        listItemPedido.forEach(itemPedido -> {
            Produto produto = itemPedido.getProduto();
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + itemPedido.getQuantidade());
            listProdutoAtualizar.add(produto);
        });
        produtoRepository.saveAll(listProdutoAtualizar);
        pedido.setStatus(EStatusPedido.CANCELADO);
        return pedidoRepository.save(pedido);
    }

    private void validar(PedidoDtoInput pedidoDtoInput) {
        if (pedidoDtoInput.getItemPedidoList().isEmpty()) {
            throw new RuntimeException("É obrigatório ter pelo menos um Item Pedido");
        }
    }


    public void deletar(Long id, String login, String senha) {
        autenticacaoService.validarUsuarioAdministrador(login, senha);
        pedidoRepository.deleteById(id);
    }
}
