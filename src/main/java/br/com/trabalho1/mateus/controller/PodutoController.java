package br.com.trabalho1.mateus.controller;

import br.com.trabalho1.mateus.dto.output.ProdutoDtoOutput;
import br.com.trabalho1.mateus.entity.Pessoa;
import br.com.trabalho1.mateus.entity.Produto;
import br.com.trabalho1.mateus.entity.Usuario;
import br.com.trabalho1.mateus.repository.UsuarioRepository;
import br.com.trabalho1.mateus.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/produto")
public class PodutoController {

    @Autowired
    ProdutoService produtoService;

    @Autowired
    UsuarioRepository usuarioRepository;


    @GetMapping("/buscarTodos")
    public ResponseEntity<?> buscarTodos(@RequestHeader("login") String login,
                                         @RequestHeader("senha") String senha) {
        Usuario usuario = usuarioRepository.findByLogin(login).orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        Pessoa pessoa = usuario.getPessoa();
        List<Produto> produtos = produtoService.buscarTodosDeAcordoComIdadePessoa(pessoa);
        List<ProdutoDtoOutput> produtosDtoOutput = ProdutoDtoOutput.listFromProduto(produtos, pessoa);
        return new ResponseEntity(produtosDtoOutput, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> buscarProdutos(@RequestHeader("login") String login,
                                            @RequestHeader("senha") String senha,
                                            @RequestParam(name = "descricao", required = false) String descricao,
                                            @RequestParam(name = "precoMinimo", required = false) BigDecimal precoMinimo,
                                            @RequestParam(name = "precoMaximo", required = false) BigDecimal precoMaximo) {
        Usuario usuario = usuarioRepository.findByLogin(login).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        if (!senha.equalsIgnoreCase(usuario.getSenha())) {
            throw new RuntimeException("Senha incorreta");
        }
        Pessoa pessoa = usuario.getPessoa();
        List<ProdutoDtoOutput> produtos = produtoService.buscarTodosLambda(descricao, precoMinimo, precoMaximo, pessoa);
        return new ResponseEntity(produtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@RequestHeader("login") String login,
                                         @RequestHeader("senha") String senha,
                                         @PathVariable Long id) {
        Usuario usuario = usuarioRepository.findByLogin(login).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        if (!senha.equalsIgnoreCase(usuario.getSenha())) {
            throw new RuntimeException("Senha incorreta");
        }
        Pessoa pessoa = usuario.getPessoa();
        Produto produto = produtoService.buscarPorId(id, pessoa);
        return new ResponseEntity(new ProdutoDtoOutput(produto, pessoa), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> inserir(@RequestHeader("login") String login,
                                     @RequestHeader("senha") String senha,
                                     @RequestBody Produto produto) {
        return new ResponseEntity(produtoService.inserir(login, senha, produto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> alterar(@PathVariable Long id,
                                     @RequestHeader("login") String login,
                                     @RequestHeader("senha") String senha,
                                     @RequestBody Produto produto) {
        return new ResponseEntity(produtoService.alterar(id, login, senha, produto), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id,
                                     @RequestHeader("login") String login,
                                     @RequestHeader("senha") String senha) {
        produtoService.deletar(id, login, senha);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
