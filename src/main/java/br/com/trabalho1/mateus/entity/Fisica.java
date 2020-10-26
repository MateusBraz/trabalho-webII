package br.com.trabalho1.mateus.entity;

import br.com.trabalho1.mateus.enuns.ESituacaoPessoa;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.List;


@Data
@ToString(exclude = {"cpf", "rg"})
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@DiscriminatorValue("FISICA")
public class Fisica extends Pessoa {

    @Column(name = "PES_CPF", unique = true, nullable = true)
    private String cpf;

    @Column(name = "PES_RG", unique = true, nullable = true)
    private String rg;

    @Builder(builderMethodName = "fisicaBuilder")
    public Fisica(Long id, List<Pedido> pedidos, Usuario usuario, Pessoa idResponsavel, ESituacaoPessoa situacao, String nome, String apelido, LocalDate dataNascimento, String cpf, String rg) {
        super(id, pedidos, usuario, idResponsavel, situacao, nome, apelido, dataNascimento);
        this.cpf = cpf;
        this.rg = rg;
    }

}
