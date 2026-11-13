package com.gestorprodutos.produto.domain.entity;

import com.gestorprodutos.produto.dto.ProdutoRequestDTO;
import com.gestorprodutos.produto.exception.BusinessRuleException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProdutoTest {

    @Test
    void deveCriarProdutoComSucesso() {
        Produto produto = new Produto(
                "test",
                new BigDecimal("11"),
                "descricao test",
                11,
                1L,
                1L
        );

        Assertions.assertEquals("test", produto.getNome());
        Assertions.assertEquals(new BigDecimal("11"), produto.getPreco());
        Assertions.assertEquals("descricao test", produto.getDescricao());
        Assertions.assertEquals(11, produto.getQuantidade());
        Assertions.assertEquals(1L, produto.getCategoriaId());
        Assertions.assertEquals(1L, produto.getMarcaId());
        Assertions.assertNull(produto.getId());
    }

    @Test
    void deveCriarProdutoComConstrutorVazio() {
        Produto produto = new Produto();

        Assertions.assertNull(produto.getId());
        Assertions.assertNull(produto.getNome());
        Assertions.assertNull(produto.getPreco());
        Assertions.assertNull(produto.getDescricao());
        Assertions.assertNull(produto.getQuantidade());
        Assertions.assertNull(produto.getCategoriaId());
        Assertions.assertNull(produto.getMarcaId());
    }

    @Test
    void deveCriarProdutoComProdutoRequestDTO() {
        ProdutoRequestDTO request = new ProdutoRequestDTO(
                "Notebook",
                new BigDecimal("3500.00"),
                "Notebook para testes",
                5,
                2L,
                3L
        );

        Produto produto = new Produto(request);

        Assertions.assertEquals("Notebook", produto.getNome());
        Assertions.assertEquals(new BigDecimal("3500.00"), produto.getPreco());
        Assertions.assertEquals("Notebook para testes", produto.getDescricao());
        Assertions.assertEquals(5, produto.getQuantidade());
        Assertions.assertEquals(2L, produto.getCategoriaId());
        Assertions.assertEquals(3L, produto.getMarcaId());
    }

    @Test
    void deveAlterarNome() {
        Produto produto = criarProduto();

        produto.setNome("Mouse");

        Assertions.assertEquals("Mouse", produto.getNome());
    }

    @Test
    void deveAlterarPreco() {
        Produto produto = criarProduto();

        produto.setPreco(new BigDecimal("99.90"));

        Assertions.assertEquals(new BigDecimal("99.90"), produto.getPreco());
    }

    @Test
    void deveAlterarDescricao() {
        Produto produto = criarProduto();

        produto.setDescricao("Nova descricao");

        Assertions.assertEquals("Nova descricao", produto.getDescricao());
    }

    @Test
    void deveAlterarQuantidade() {
        Produto produto = criarProduto();

        produto.setQuantidade(20);

        Assertions.assertEquals(20, produto.getQuantidade());
    }

    @Test
    void deveAlterarCategoriaId() {
        Produto produto = criarProduto();

        produto.setCategoriaId(5L);

        Assertions.assertEquals(5L, produto.getCategoriaId());
    }

    @Test
    void deveAlterarMarcaId() {
        Produto produto = criarProduto();

        produto.setMarcaId(7L);

        Assertions.assertEquals(7L, produto.getMarcaId());
    }

    @Test
    void deveAceitarPrecoZero() {
        Produto produto = criarProduto();

        produto.setPreco(BigDecimal.ZERO);

        Assertions.assertEquals(BigDecimal.ZERO, produto.getPreco());
    }

    @Test
    void deveLancarExcecaoQuandoPrecoForNegativo() {
        Produto produto = criarProduto();

        Assertions.assertThrows(
                BusinessRuleException.class,
                () -> produto.setPreco(new BigDecimal("-1"))
        );
    }

    @Test
    void deveLancarExcecaoQuandoPrecoForNulo() {
        Produto produto = criarProduto();

        Assertions.assertThrows(
                BusinessRuleException.class,
                () -> produto.setPreco(null)
        );
    }

    private Produto criarProduto() {
        return new Produto(
                "Produto teste",
                new BigDecimal("10.00"),
                "Descricao teste",
                10,
                1L,
                1L
        );
    }
}