package com.gestorprodutos.produto.service;


import com.gestorprodutos.produto.domain.entity.Produto;
import com.gestorprodutos.produto.domain.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto atualizar(Long id, Produto produtoAtualizado) {

        Produto produto = buscarPorId(id);

        produto.setNome(produtoAtualizado.getNome());
        produto.setPreco(produtoAtualizado.getPreco());
        produto.setDescricao(produtoAtualizado.getDescricao());
        produto.setQuantidade(produtoAtualizado.getQuantidade());
        produto.setCategoriaId(produtoAtualizado.getCategoriaId());
        produto.setMarcaId(produtoAtualizado.getMarcaId());

        return produtoRepository.save(produto);
    }

    public void deletar(Long id) {
        Produto produto = buscarPorId(id);
        produtoRepository.delete(produto);
    }
    public boolean existePorCategoria(Long categoriaId) {
        return produtoRepository.existsByCategoriaId(categoriaId);
    }

    public boolean existePorMarca(Long marcaId) {
        return produtoRepository.existsByMarcaId(marcaId);
    }
}