package br.com.trabalho1.mateus.service;

import br.com.trabalho1.mateus.dto.PedidoDtoInput;
import br.com.trabalho1.mateus.entity.Pedido;
import br.com.trabalho1.mateus.entity.Usuario;
import br.com.trabalho1.mateus.enuns.EStatusPedido;
import br.com.trabalho1.mateus.repository.PedidoRepository;
import br.com.trabalho1.mateus.repository.PessoaRepository;
import br.com.trabalho1.mateus.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PedidoService {


    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    PessoaRepository pessoaRepository;

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
        Pedido pedido = Pedido.pedidoBuilder()
                .id(null)
                .pessoa(usuario.getPessoa())
                .itemPedidoList(pedidoDtoInput.getItemPedidoList())
                .status(EStatusPedido.REALIZADO)
                .dataCompra(LocalDate.now())
                .dataEntrega(pedidoDtoInput.getDataEntrega())
                .percentualDesconto(pedidoDtoInput.getPercentualDesconto())
                .build();

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
