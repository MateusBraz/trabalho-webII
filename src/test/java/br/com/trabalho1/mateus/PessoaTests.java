package br.com.trabalho1.mateus;

import br.com.trabalho1.mateus.entity.Fisica;
import br.com.trabalho1.mateus.entity.Pessoa;
import br.com.trabalho1.mateus.enuns.ESituacaoPessoa;
import br.com.trabalho1.mateus.repository.PessoaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

@SpringBootTest
public class PessoaTests {

    @Autowired
    PessoaRepository pessoaRepository;

    @Test
    void salvar() {
        Pessoa responsavel = pessoaRepository.findById(1L).orElseThrow(() -> new RuntimeException("Pessoa não encontrada na base de dados"));
        Fisica pessoa = Fisica.fisicaBuilder()
                .id(null)
                .pedidos(new ArrayList<>())
                .usuario(null)
                .idResponsavel(responsavel)
                .situacao(ESituacaoPessoa.ATIVO)
                .nome("José menor")
                .apelido("menor")
                .dataNascimento(LocalDate.of(2007, 06, 23))
                .cpf("123.456.789-19")
                .rg("123456789")
                .build();

        pessoaRepository.save(pessoa);
    }

    @Test
    void diferencaData(){
        LocalDate localDate1 = LocalDate.of(1996, 03, 9);
        LocalDate localDate2 = LocalDate.now();
        System.out.println("data nascimento:" + localDate1);
        Period period1 = Period.between(localDate1, localDate2);
        System.out.println("diferenca das datas: " + period1.getYears());
        System.out.println(period1.getYears() < 18);
    }

    @Test
    void buscarResponsavelPeloId(){
        Pessoa pessoa = pessoaRepository.findByIdResponsavelAndId(1L);

        System.out.println("Responsável pelo: " + pessoa.getNome());
    }

    @Test
    void buscarPessoa(){
        Pessoa pessoa = pessoaRepository.findByCpf("123.456.789-19");
        System.out.println("NOME DA PESSOA: " + pessoa.getNome());
    }

    @Test
    void imprimirTipo(){
        Pessoa pessoa = pessoaRepository.findById(152L).orElseThrow(() -> new RuntimeException("Não encontrado"));
        System.out.println("Pessoa do tipo: " + pessoa.toString().substring(0, pessoa.toString().length() - 2).toUpperCase());
    }

    @Test
    void atualizarPessoa(){
        Fisica fisica = (Fisica) pessoaRepository.findById(152L).orElseThrow(() -> new RuntimeException("Não encontrado"));
        fisica.setNome("Menor alterado");
        fisica.setSituacao(ESituacaoPessoa.INATIVO);
        fisica.setCpf("32343445");
        fisica.setRg("2334424");

        pessoaRepository.save(fisica);
    }
}
