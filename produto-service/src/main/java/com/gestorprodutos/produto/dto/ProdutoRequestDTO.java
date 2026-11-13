package com.gestorprodutos.produto.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProdutoRequestDTO(

        @NotBlank(message = "O nome do produto é obrigatório")
        String nome,

        @NotNull(message = "O preço do produto é obrigatório")
        @DecimalMin(
                value = "0.1",
                message = "O preço do produto não pode ser negativo"
        )
        BigDecimal preco,

        String descricao,
        Long categoriaId,
        Long marcaId
) {
}