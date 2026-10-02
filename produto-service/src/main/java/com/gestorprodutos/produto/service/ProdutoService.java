package com.gestorprodutos.produto.service;

import com.gestorprodutos.produto.dto.ProdutoRequestDTO;
import com.gestorprodutos.produto.dto.ProdutoResponseDTO;
import com.gestorprodutos.produto.exception.ResourceNotFoundException;
import com.gestorprodutos.produto.domain.entity.Produto;
import com.gestorprodutos.produto.domain.repository.ProdutoRepository;
import com.gestorprodutos.produto.dto.CategoriaResponseDTO;
import com.gestorprodutos.produto.dto.MarcaResponseDTO;
import com.gestorprodutos.produto.dto.ProdutoDetalhadoResponseDTO;
import com.gestorprodutos.produto.infrastructure.restClient.CategoriaRestClient;
import com.gestorprodutos.produto.infrastructure.restClient.MarcaRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final MarcaRestClient marcaRestClient;
    private final CategoriaRestClient categoriaRestClient;

    public Page<ProdutoDetalhadoResponseDTO> listarTodos(Pageable pageable) {

        return produtoRepository.findAll(pageable)
                .map(produto -> new ProdutoDetalhadoResponseDTO(
                        produto.getId(),
                        produto.getNome(),
                        produto.getPreco(),
                        produto.getDescricao(),
                        produto.getQuantidade(),
                        marcaRestClient.buscarPorId(produto.getMarcaId()),
                        categoriaRestClient.buscarPorId(produto.getCategoriaId())
                ));
    }

    public ProdutoDetalhadoResponseDTO buscarPorId(Long id) {

        Produto produto = buscarEntidadePorId(id);

        MarcaResponseDTO marca = marcaRestClient.buscarPorId(produto.getMarcaId());

        CategoriaResponseDTO categoria = categoriaRestClient.buscarPorId(produto.getCategoriaId());

        return new ProdutoDetalhadoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getPreco(),
                produto.getDescricao(),
                produto.getQuantidade(),
                marca,
                categoria
        );
    }

    private Produto buscarEntidadePorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Produto não encontrado com o ID: " + id
                        ));
    }

    public ProdutoResponseDTO salvar(ProdutoRequestDTO request) {

        marcaRestClient.buscarPorId(request.marcaId());

        categoriaRestClient.buscarPorId(request.categoriaId());

        Produto produto = new Produto();

        produto.setNome(request.nome());
        produto.setPreco(request.preco());
        produto.setDescricao(request.descricao());
        produto.setQuantidade(request.quantidade());
        produto.setCategoriaId(request.categoriaId());
        produto.setMarcaId(request.marcaId());

        Produto produtoSalvo = produtoRepository.save(produto);

        return new ProdutoResponseDTO(
                produtoSalvo.getId(),
                produtoSalvo.getNome(),
                produtoSalvo.getPreco(),
                produtoSalvo.getDescricao(),
                produtoSalvo.getQuantidade(),
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

        produto.setNome(request.nome());
        produto.setPreco(request.preco());
        produto.setDescricao(request.descricao());
        produto.setQuantidade(request.quantidade());
        produto.setCategoriaId(request.categoriaId());
        produto.setMarcaId(request.marcaId());

        Produto produtoAtualizado = produtoRepository.save(produto);

        return new ProdutoResponseDTO(
                produtoAtualizado.getId(),
                produtoAtualizado.getNome(),
                produtoAtualizado.getPreco(),
                produtoAtualizado.getDescricao(),
                produtoAtualizado.getQuantidade(),
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