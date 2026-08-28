package com.gestorprodutos.produto.controller;

import com.gestorprodutos.produto.service.ProdutoService;
import com.gestorprodutos.produto.domain.entity.Produto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        return ResponseEntity.ok(produtoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Produto> salvar(@RequestBody Produto produto) {
        return ResponseEntity.ok(produtoService.salvar(produto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(
            @PathVariable Long id,
            @RequestBody Produto produto
    ) {
        return ResponseEntity.ok(
                produtoService.atualizar(id, produto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categoria/{categoriaId}/existe")
    public ResponseEntity<Boolean> existePorCategoria(
            @PathVariable Long categoriaId) {

        return ResponseEntity.ok(
                produtoService.existePorCategoria(categoriaId)
        );
    }

    @GetMapping("/marca/{marcaId}/existe")
    public ResponseEntity<Boolean> existePorMarca(
            @PathVariable Long marcaId) {

        return ResponseEntity.ok(
                produtoService.existePorMarca(marcaId)
        );
    }
}