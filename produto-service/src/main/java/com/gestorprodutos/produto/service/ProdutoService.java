package com.gestorprodutos.produto.service;

import com.gestorprodutos.produto.domain.entity.Produto;
import com.gestorprodutos.produto.domain.repository.ProdutoRepository;
import com.gestorprodutos.produto.dto.CategoriaResponseDTO;
import com.gestorprodutos.produto.dto.MarcaResponseDTO;
import com.gestorprodutos.produto.dto.ProdutoDetalhadoResponseDTO;
import com.gestorprodutos.produto.dto.ProdutoRequestDTO;
import com.gestorprodutos.produto.dto.ProdutoResponseDTO;
import com.gestorprodutos.produto.exception.ExternalServiceException;
import com.gestorprodutos.produto.exception.ResourceNotFoundException;
import com.gestorprodutos.produto.infrastructure.restClient.CategoriaRestClient;
import com.gestorprodutos.produto.infrastructure.restClient.MarcaRestClient;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final MarcaRestClient marcaRestClient;
    private final CategoriaRestClient categoriaRestClient;

    public Page<ProdutoDetalhadoResponseDTO> listarTodos(Pageable pageable) {

        return produtoRepository.findAll(pageable)
                .map(produto -> {

                    List<ExternalServiceException> erros = new ArrayList<>();

                    MarcaResponseDTO marca = buscarComTratamento(
                            () -> marcaRestClient.buscarPorId(produto.getMarcaId()),
                            erros
                    );

                    CategoriaResponseDTO categoria = buscarComTratamento(
                            () -> categoriaRestClient.buscarPorId(produto.getCategoriaId()),
                            erros
                    );

                    tratarErrosExternos(erros);

                    return new ProdutoDetalhadoResponseDTO(
                            produto.getId(),
                            produto.getNome(),
                            produto.getPreco(),
                            produto.getDescricao(),
                            marca,
                            categoria
                    );
                });
    }

    public ProdutoDetalhadoResponseDTO buscarPorId(Long id) {

        Produto produto = buscarEntidadePorId(id);

        List<ExternalServiceException> erros = new ArrayList<>();

        MarcaResponseDTO marca = buscarComTratamento(
                () -> marcaRestClient.buscarPorId(produto.getMarcaId()),
                erros
        );

        CategoriaResponseDTO categoria = buscarComTratamento(
                () -> categoriaRestClient.buscarPorId(produto.getCategoriaId()),
                erros
        );

        tratarErrosExternos(erros);

        return new ProdutoDetalhadoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getPreco(),
                produto.getDescricao(),
                marca,
                categoria
        );
    }

    private <T> T buscarComTratamento(
            Supplier<T> busca,
            List<ExternalServiceException> erros
    ) {

        try {
            return busca.get();

        } catch (ExternalServiceException ex) {
            erros.add(ex);
            return null;
        }
    }

    private void tratarErrosExternos(
            List<ExternalServiceException> erros
    ) {

        if (erros.size() == 2) {
            throw new ExternalServiceException(
                    "Impossivel conectar-se com a marca e categoria no momento"
            );
        }

        if (!erros.isEmpty()) {
            throw erros.get(0);
        }
    }

    public Produto buscarEntidadePorId(Long id) {

        return produtoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Produto não encontrado com o ID: " + id
                        )
                );
    }

    public ProdutoResponseDTO salvar(ProdutoRequestDTO request) {

        marcaRestClient.buscarPorId(request.marcaId());
        categoriaRestClient.buscarPorId(request.categoriaId());

        Produto produto = new Produto(request);

        Produto produtoSalvo = produtoRepository.save(produto);

        return new ProdutoResponseDTO(
                produtoSalvo.getId(),
                produtoSalvo.getNome(),
                produtoSalvo.getPreco(),
                produtoSalvo.getDescricao(),
                produtoSalvo.getCategoriaId(),
                produtoSalvo.getMarcaId()
        );
    }

    public ProdutoResponseDTO atualizar(
            Long id,
            ProdutoRequestDTO request
    ) {

        Produto produto = buscarEntidadePorId(id);

        marcaRestClient.buscarPorId(request.marcaId());
        categoriaRestClient.buscarPorId(request.categoriaId());

        Produto produtoComNovosDados = new Produto(request);

        produto.setNome(produtoComNovosDados.getNome());
        produto.setPreco(produtoComNovosDados.getPreco());
        produto.setDescricao(produtoComNovosDados.getDescricao());
        produto.setCategoriaId(produtoComNovosDados.getCategoriaId());
        produto.setMarcaId(produtoComNovosDados.getMarcaId());

        Produto produtoAtualizado = produtoRepository.save(produto);

        return new ProdutoResponseDTO(
                produtoAtualizado.getId(),
                produtoAtualizado.getNome(),
                produtoAtualizado.getPreco(),
                produtoAtualizado.getDescricao(),
                produtoAtualizado.getCategoriaId(),
                produtoAtualizado.getMarcaId()
        );
    }

    public void deletar(Long id) {

        Produto produto = buscarEntidadePorId(id);

        produtoRepository.delete(produto);
    }

    public boolean existePorCategoria(Long categoriaId) {

        return produtoRepository.existsByCategoriaId(categoriaId);
    }

    public boolean existePorMarca(Long marcaId) {

        return produtoRepository.existsByMarcaId(marcaId);
    }
}