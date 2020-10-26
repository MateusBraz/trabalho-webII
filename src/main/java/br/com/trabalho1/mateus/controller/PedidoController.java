package br.com.trabalho1.mateus.controller;


import br.com.trabalho1.mateus.dto.PedidoDtoInput;
import br.com.trabalho1.mateus.entity.Pedido;
import br.com.trabalho1.mateus.entity.Usuario;
import br.com.trabalho1.mateus.repository.UsuarioRepository;
import br.com.trabalho1.mateus.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return new ResponseEntity(pedidoService.buscarTodos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@RequestHeader("login") String login,
                                         @RequestHeader("senha") String senha,
                                         @PathVariable Long id) {
        Usuario usuario = usuarioRepository.findByLoginAndSenha(login, senha).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return new ResponseEntity(pedidoService.buscarPorId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> inserir(@RequestHeader("login") String login,
                                     @RequestHeader("senha") String senha,
                                     @RequestBody PedidoDtoInput pedidoDtoInput) {
        return new ResponseEntity(pedidoService.inserir(login, senha, pedidoDtoInput), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> alterar(@PathVariable Long id,
                                     @RequestHeader("login") String login,
                                     @RequestHeader("senha") String senha,
                                     @RequestBody Pedido pedido) {
        return new ResponseEntity(pedidoService.alterar(id, pedido, login, senha), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id,
                                     @RequestHeader("login") String login,
                                     @RequestHeader("senha") String senha) {
        pedidoService.deletar(id, login, senha);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
