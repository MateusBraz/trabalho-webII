package br.com.trabalho1.mateus.service;

import br.com.trabalho1.mateus.dto.input.ItemPedidoDtoInput;
import br.com.trabalho1.mateus.entity.ItemPedido;
import br.com.trabalho1.mateus.entity.Pedido;
import br.com.trabalho1.mateus.entity.Produto;
import br.com.trabalho1.mateus.entity.Usuario;
import br.com.trabalho1.mateus.repository.ItemPedidoRepository;
import br.com.trabalho1.mateus.repository.ProdutoRepository;
import br.com.trabalho1.mateus.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemPedidoService {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<ItemPedido> buscarTodos() {
        return itemPedidoRepository.findAll();
    }

    public ItemPedido buscarPorId(Long id) {
        return itemPedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Item pedido com id " + id + " não encontrado"));
    }

    public ItemPedido salvar(String login, String senha, ItemPedidoDtoInput itemPedidoDtoInput) {
        autenticacaoService.validarUsuarioAdministrador(login, senha);
        if(itemPedidoDtoInput.getIdPedido() == null){
            throw new RuntimeException("Id do pedido não pode ser nulo");
        }
        Pedido pedido = pedidoService.buscarPorId(itemPedidoDtoInput.getIdPedido());
        Produto produto = produtoService.buscarPorIdProduto(itemPedidoDtoInput.getIdProduto());
        ItemPedido itemPedido = ItemPedido.itemPedidoBuilder()
                .pedido(pedido)
                .produto(produto)
                .quantidade(itemPedidoDtoInput.getQuantidade())
                .build();

        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - itemPedidoDtoInput.getQuantidade());
        produtoRepository.save(produto);

        return itemPedidoRepository.save(itemPedido);
    }

//    public ItemPedido alterar(String login, String senha, Long id, ItemPedidoDtoInput itemPedidoDtoInput) {
//        validarUsuarioAdministrador(login, senha);
//        ItemPedido itemPedido = buscarPorId(id);
//
//        return itemPedidoRepository.save(itemPedido);
//    }

    public void deletar(Long id) {
        itemPedidoRepository.deleteById(id);
    }

}
