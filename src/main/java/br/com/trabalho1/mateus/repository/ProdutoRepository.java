package br.com.trabalho1.mateus.repository;

import br.com.trabalho1.mateus.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("select p from Produto p where ?1 >= p.idadePermitida")
    List<Produto> findByIdadePermitida(Integer idade);

}
