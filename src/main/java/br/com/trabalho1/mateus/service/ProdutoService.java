package br.com.trabalho1.mateus.service;

import br.com.trabalho1.mateus.dto.output.ProdutoDtoOutput;
import br.com.trabalho1.mateus.entity.Pessoa;
import br.com.trabalho1.mateus.entity.Produto;
import br.com.trabalho1.mateus.entity.Usuario;
import br.com.trabalho1.mateus.repository.ProdutoRepository;
import br.com.trabalho1.mateus.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public Produto buscarPorIdProduto(Long id){
        return produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto com id " + id + ", não encontrado na base de dados"));
    }

    public List<Produto> buscarTodosDeAcordoComIdadePessoa(Pessoa pessoa) {
        LocalDate dataAtual = LocalDate.now();
        Period idade = Period.between(pessoa.getDataNascimento(), dataAtual);
        return produtoRepository.findByIdadePermitida(idade.getYears());
    }

    public List<ProdutoDtoOutput> buscarTodosLambda(String descricao, BigDecimal precoMinimo, BigDecimal precoMaximo, Pessoa pessoa) {
        List<ProdutoDtoOutput> listaLambda = buscarTodosDeAcordoComIdadePessoa(pessoa)
                .stream()
                .map(p -> new ProdutoDtoOutput(p, pessoa))
                .filter(p -> p.getDescricao().equals(descricao))
                .filter(p -> p.getPrecoVenda().compareTo(precoMinimo) >=0)
                .filter(p -> p.getPrecoVenda().compareTo(precoMaximo) <=0)
                .collect(Collectors.toList());
        return listaLambda;
    }

    public Produto buscarPorId(Long id, Pessoa pessoa) {
        LocalDate dataAtual = LocalDate.now();
        Period idade = Period.between(pessoa.getDataNascimento(), dataAtual);
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto com id " + id + ", não encontrado na base de dados"));
        if (idade.getYears() < produto.getIdadePermitida()) {
            throw new RuntimeException("Pessoa não possui a idade permitida para acessar esse produto");
        }
        return produto;
    }

    public Produto inserir(String login, String senha, Produto produto) {
        validarUsuarioAdministrador(login, senha);
        return produtoRepository.save(produto);
    }

    public Produto alterar(Long id, String login, String senha, Produto produto) {
        validarUsuarioAdministrador(login, senha);
        Produto produtoAlterar = produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto com id " + id + ", não encontrado na base de dados"));
        produtoAlterar.setDescricao(produto.getDescricao());
        produtoAlterar.setQuantidadeEstoque(produto.getQuantidadeEstoque());
        produtoAlterar.setIdadePermitida(produto.getIdadePermitida());
        produtoAlterar.setPrecoCompra(produto.getPrecoCompra());
        produtoAlterar.setPrecoVendaFisica(produto.getPrecoVendaFisica());
        produtoAlterar.setPrecoVendaJuridica(produto.getPrecoVendaJuridica());
        return produtoRepository.save(produtoAlterar);
    }

    public void deletar(Long id, String login, String senha) {
        validarUsuarioAdministrador(login, senha);
        produtoRepository.deleteById(id);
    }

    private void validarUsuarioAdministrador(String login, String senha) {
        Usuario usuarioAutenticado = usuarioRepository.findByLoginAndSenha(login, senha).orElseThrow(() -> new RuntimeException("Usuário com login " + login + ", não encontrado na base de dados"));
        if (!usuarioAutenticado.getIsAdministrador()) {
            throw new RuntimeException("Usuário não tem permissão para fazer essa operação");
        }
    }

}
