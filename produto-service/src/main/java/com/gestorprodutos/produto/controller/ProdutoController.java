package com.gestorprodutos.produto.controller;

import com.gestorprodutos.produto.service.ProdutoService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.gestorprodutos.produto.dto.ProdutoDetalhadoResponseDTO;
import com.gestorprodutos.produto.dto.ProdutoRequestDTO;
import com.gestorprodutos.produto.dto.ProdutoResponseDTO;


@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoDetalhadoResponseDTO>> listarTodos(Pageable pageable) {
        return ResponseEntity.ok(produtoService.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDetalhadoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> salvar(
            @RequestBody ProdutoRequestDTO request
    ) {
        return ResponseEntity.ok(produtoService.salvar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody ProdutoRequestDTO request
    ) {
        return ResponseEntity.ok(
                produtoService.atualizar(id, request)
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