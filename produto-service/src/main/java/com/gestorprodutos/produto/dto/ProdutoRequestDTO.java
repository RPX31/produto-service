package com.gestorprodutos.produto.dto;

import java.math.BigDecimal;

public record ProdutoRequestDTO(
        String nome,
        BigDecimal preco,
        String descricao,
        Integer quantidade,
        Long categoriaId,
        Long marcaId
) {
}