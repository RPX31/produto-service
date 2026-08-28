package com.gestorprodutos.produto.domain.repository;

import com.gestorprodutos.produto.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    boolean existsByCategoriaId(Long categoriaId);

    boolean existsByMarcaId(Long marcaId);

}