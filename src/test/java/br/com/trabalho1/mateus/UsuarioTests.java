package br.com.trabalho1.mateus;

import br.com.trabalho1.mateus.entity.Fisica;
import br.com.trabalho1.mateus.entity.Pessoa;
import br.com.trabalho1.mateus.entity.Usuario;
import br.com.trabalho1.mateus.enuns.ESituacaoPessoa;
import br.com.trabalho1.mateus.repository.PessoaRepository;
import br.com.trabalho1.mateus.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootTest
public class UsuarioTests {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PessoaRepository pessoaRepository;

    @Test
    void salvar() {
        Pessoa pessoa = pessoaRepository.findById(1L).orElseThrow(() -> new RuntimeException("Pessoa não encontrada na base de dados"));
//        Fisica pessoa = Fisica.fisicaBuilder()
//                .id(null)
//                .pedidos(new ArrayList<>())
//                .idResponsavel(null)
//                .situacao(ESituacaoPessoa.ATIVO)
//                .nome("Mateus Braz")
//                .apelido("Braz")
//                .dataNascimento(LocalDate.of(1996, 03, 9))
//                .cpf("123.456.789-32")
//                .rg("123456")
//                .build();

        Usuario usuario = Usuario.builder()
                .pessoa(pessoa)
                .isAdministrador(true)
                .login("mateus.braz")
                .senha("1234567")
                .build();

        usuarioRepository.save(usuario);
    }
}
