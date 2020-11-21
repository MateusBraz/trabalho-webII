package br.com.trabalho1.mateus.controller;


import br.com.trabalho1.mateus.dto.input.PedidoDtoInput;
import br.com.trabalho1.mateus.dto.output.PedidoDtoOutput;
import br.com.trabalho1.mateus.entity.Pedido;
import br.com.trabalho1.mateus.entity.Usuario;
import br.com.trabalho1.mateus.repository.UsuarioRepository;
import br.com.trabalho1.mateus.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/pedido")
public class PedidoController {

    @Autowired
    PedidoService pedidoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<?> buscarTodos(@RequestHeader("login") String login,
                                         @RequestHeader("senha") String senha) {
        Usuario usuario = usuarioRepository.findByLoginAndSenha(login, senha).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        List<Pedido> pedidos = pedidoService.buscarTodos();
        return new ResponseEntity(PedidoDtoOutput.listFromPedido(pedidos), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@RequestHeader("login") String login,
                                         @RequestHeader("senha") String senha,
                                         @PathVariable Long id) {
        Usuario usuario = usuarioRepository.findByLoginAndSenha(login, senha).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Pedido pedido = pedidoService.buscarPorId(id);
        return new ResponseEntity(new PedidoDtoOutput(pedido), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> inserir(@RequestHeader("login") String login,
                                     @RequestHeader("senha") String senha,
                                     @RequestBody PedidoDtoInput pedidoDtoInput) {
        Pedido pedido = pedidoService.inserir(login, senha, pedidoDtoInput);
        return new ResponseEntity(new PedidoDtoOutput(pedido), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> alterar(@PathVariable Long id,
                                     @RequestHeader("login") String login,
                                     @RequestHeader("senha") String senha,
                                     @RequestBody Pedido pedido) {
        Pedido pedidoAlterado = pedidoService.alterar(id, pedido, login, senha);
        return new ResponseEntity(new PedidoDtoOutput(pedidoAlterado), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id,
                                     @RequestHeader("login") String login,
                                     @RequestHeader("senha") String senha) {
        pedidoService.deletar(id, login, senha);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
