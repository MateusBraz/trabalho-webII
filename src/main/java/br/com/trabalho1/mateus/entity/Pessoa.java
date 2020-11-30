package br.com.trabalho1.mateus.entity;

import br.com.trabalho1.mateus.enuns.ESituacaoPessoa;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.engine.spi.PersistentAttributeInterceptable;
import org.hibernate.engine.spi.PersistentAttributeInterceptor;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_PESSOA")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "PES_TIPO")
@SequenceGenerator(name = "seq_pessoa")
public abstract class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_pessoa")
    @Column(name = "PES_ID")
    private Long id;

    @JsonIgnore
    @OneToMany(mappedBy = "pessoa")
    private List<Pedido> pedidos;

    @JsonIgnore
    @OneToOne(mappedBy = "pessoa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Usuario usuario;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "ID_RESPONSAVEL")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonBackReference
    private Pessoa idResponsavel;

    @Enumerated(EnumType.STRING)
    @Column(name = "PES_SITUACAO")
    private ESituacaoPessoa situacao;

    @Column(name = "PES_NOME")
    private String nome;

    @Column(name = "PES_APELIDO")
    private String apelido;

    @Column(name = "PES_DATANASCIMENTO")
    private LocalDate dataNascimento;

}
