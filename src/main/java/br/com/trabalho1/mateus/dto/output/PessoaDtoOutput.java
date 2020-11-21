package br.com.trabalho1.mateus.dto.output;

import br.com.trabalho1.mateus.entity.Pessoa;
import br.com.trabalho1.mateus.enuns.ESituacaoPessoa;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PessoaDtoOutput {

    private Long id;
    private String nome;
    private ESituacaoPessoa situacao;
    private LocalDate dataNascimento;
    private String tipo;


    public PessoaDtoOutput(Pessoa pessoa){
        this.id = pessoa.getId();
        this.nome = pessoa.getNome();
        this.situacao = pessoa.getSituacao();
        this.dataNascimento = pessoa.getDataNascimento();
        this.tipo = pessoa.toString();
    }

}
