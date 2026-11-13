package com.gestorprodutos.produto.dto;

import java.math.BigDecimal;

public record ProdutoDetalhadoResponseDTO(
        Long id,
        String nome,
        BigDecimal preco,
        String descricao,
        MarcaResponseDTO marca,
        CategoriaResponseDTO categoria
) {
}