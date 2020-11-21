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
import java.util.List;

@SpringBootTest
public class PessoaTests {

    @Autowired
    PessoaRepository pessoaRepository;

    @Test
    void salvar() {
//        Pessoa responsavel = pessoaRepository.findById(1L).orElseThrow(() -> new RuntimeException("Pessoa não encontrada na base de dados"));
        List<Pessoa> list = new ArrayList<>();
        Fisica pessoa1 = Fisica.fisicaBuilder()
                .id(null)
                .pedidos(new ArrayList<>())
                .usuario(null)
                .idResponsavel(null)
                .situacao(ESituacaoPessoa.ATIVO)
                .nome("Mateus Silva")
                .apelido("silva")
                .dataNascimento(LocalDate.of(1996, 06, 23))
                .cpf("123.156.759-19")
                .rg("129")
                .build();

        list.add(pessoa1);

        Fisica pessoa2 = Fisica.fisicaBuilder()
                .id(null)
                .pedidos(new ArrayList<>())
                .usuario(null)
                .idResponsavel(pessoa1)
                .situacao(ESituacaoPessoa.ATIVO)
                .nome("Marcos")
                .apelido("marquin")
                .dataNascimento(LocalDate.of(2012, 06, 23))
                .cpf("000.156.789-19")
                .rg("12654569")
                .build();

        list.add(pessoa2);

        Fisica pessoa3 = Fisica.fisicaBuilder()
                .id(null)
                .pedidos(new ArrayList<>())
                .usuario(null)
                .idResponsavel(pessoa1)
                .situacao(ESituacaoPessoa.ATIVO)
                .nome("João")
                .apelido("Joãzika")
                .dataNascimento(LocalDate.of(2012, 06, 23))
                .cpf("589.156.789-19")
                .rg("17584569")
                .build();

        list.add(pessoa3);

        Fisica pessoa4 = Fisica.fisicaBuilder()
                .id(null)
                .pedidos(new ArrayList<>())
                .usuario(null)
                .idResponsavel(pessoa1)
                .situacao(ESituacaoPessoa.ATIVO)
                .nome("Maria")
                .apelido("mari")
                .dataNascimento(LocalDate.of(2005, 06, 23))
                .cpf("123.156.652-79")
                .rg("125874")
                .build();

        list.add(pessoa4);

        Fisica pessoa5 = Fisica.fisicaBuilder()
                .id(null)
                .pedidos(new ArrayList<>())
                .usuario(null)
                .idResponsavel(null)
                .situacao(ESituacaoPessoa.ATIVO)
                .nome("Fagner")
                .apelido("Fag")
                .dataNascimento(LocalDate.of(2008, 06, 23))
                .cpf("123.164.789-19")
                .rg("1234478")
                .build();

        list.add(pessoa5);

        Fisica pessoa6 = Fisica.fisicaBuilder()
                .id(null)
                .pedidos(new ArrayList<>())
                .usuario(null)
                .idResponsavel(null)
                .situacao(ESituacaoPessoa.ATIVO)
                .nome("Jorge")
                .apelido("Jorgin")
                .dataNascimento(LocalDate.of(1996, 06, 23))
                .cpf("123.056.762-19")
                .rg("128289")
                .build();

        list.add(pessoa6);

        pessoaRepository.saveAll(list);
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
