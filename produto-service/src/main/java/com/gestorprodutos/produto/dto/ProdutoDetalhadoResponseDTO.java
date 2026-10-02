package com.gestorprodutos.produto.dto;

import java.math.BigDecimal;

public record ProdutoDetalhadoResponseDTO(
        Long id,
        String nome,
        BigDecimal preco,
        String descricao,
        Integer quantidade,
        MarcaResponseDTO marca,
        CategoriaResponseDTO categoria
) {
}
