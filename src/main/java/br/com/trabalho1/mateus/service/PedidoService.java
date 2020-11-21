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
        validarUsuarioAdministrador(login, senha);
        validar(pedidoDtoInput);
        Usuario usuario = usuarioRepository.findByLoginAndSenha(login, senha).orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        List<Produto> listProdutoAtualizar = new ArrayList<>();
        Pessoa pessoa = pessoaService.buscarPorId(pedidoDtoInput.getIdPessoa());

        Pedido pedido = new Pedido();
        pedido.setPessoa(pessoa);
        pedido.setStatus(EStatusPedido.REALIZADO);
        pedido.setDataCompra(LocalDate.now());
        pedido.setDataEntrega(pedidoDtoInput.getDataEntrega());
        pedido.setPercentualDesconto(pedidoDtoInput.getPercentualDesconto());

        List<ItemPedido> listItemPedido = pedidoDtoInput.getItemPedidoList()
                .stream()
                .map(itemPedidoDtoInput -> {
                    Produto produto = produtoService.buscarPorIdProduto(itemPedidoDtoInput.getIdProduto());
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

    public Pedido alterar(Long id, Pedido pedido, String login, String senha) {
        return pedidoRepository.save(pedido);
    }

    private void validar(PedidoDtoInput pedidoDtoInput) {
        if (pedidoDtoInput.getItemPedidoList().isEmpty()) {
            throw new RuntimeException("É obrigatório ter pelo menos um Item Pedido");
        }
    }

    private void validarUsuarioAdministrador(String login, String senha) {
        Usuario usuarioAutenticado = usuarioRepository.findByLoginAndSenha(login, senha).orElseThrow(() -> new RuntimeException("Usuário com login " + login + ", não encontrado na base de dados"));
        if (!usuarioAutenticado.getIsAdministrador()) {
            throw new RuntimeException("Usuário não tem permissão para fazer essa operação");
        }
    }

    public void deletar(Long id, String login, String senha) {
        validarUsuarioAdministrador(login, senha);
        pedidoRepository.deleteById(id);
    }
}
