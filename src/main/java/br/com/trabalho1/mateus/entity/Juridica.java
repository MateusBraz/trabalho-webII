package br.com.trabalho1.mateus.entity;

import br.com.trabalho1.mateus.enuns.ESituacaoPessoa;
import lombok.*;


import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.List;

@Data
@ToString(exclude = "cnpj")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@DiscriminatorValue("JURIDICA")
public class Juridica extends Pessoa{

    @Column(name = "PES_CNPJ", unique = true, nullable = true)
    private String cnpj;

    @Builder(builderMethodName = "juridicaBuilder")
    public Juridica(Long id, List<Pedido> pedidos, Usuario usuario, Pessoa idResponsavel, ESituacaoPessoa situacao, String nome, String apelido, LocalDate dataNascimento, String cnpj) {
        super(id, pedidos, usuario, idResponsavel, situacao, nome, apelido, dataNascimento);
        this.cnpj = cnpj;
    }

}
