package com.gestorprodutos.produto.service;

import com.gestorprodutos.produto.domain.entity.Produto;
import com.gestorprodutos.produto.domain.repository.ProdutoRepository;
import com.gestorprodutos.produto.dto.ProdutoRequestDTO;
import com.gestorprodutos.produto.dto.ProdutoResponseDTO;
import com.gestorprodutos.produto.infrastructure.restClient.CategoriaRestClient;
import com.gestorprodutos.produto.infrastructure.restClient.MarcaRestClient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService service;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private MarcaRestClient marcaRestClient;

    @Mock
    private CategoriaRestClient categoriaRestClient;

    @Test
    void deletarComSucesso() {

        Produto produto = new Produto();

        when(produtoRepository.findById(1L))
                .thenReturn(Optional.of(produto));

        service.deletar(1L);

        verify(produtoRepository).findById(1L);
        verify(produtoRepository).delete(produto);
    }

    @Test
    void salvarComSucesso() {

        ProdutoRequestDTO request = new ProdutoRequestDTO(
                "Teclado",
                new BigDecimal("150.00"),
                "Teclado mecanico",
                10,
                1L,
                1L
        );

        Produto produtoSalvo = new Produto(request);

        when(produtoRepository.save(any(Produto.class)))
                .thenReturn(produtoSalvo);

        ProdutoResponseDTO resposta = service.salvar(request);

        assertNotNull(resposta);

        verify(marcaRestClient).buscarPorId(1L);
        verify(categoriaRestClient).buscarPorId(1L);
        verify(produtoRepository).save(any(Produto.class));
    }
}