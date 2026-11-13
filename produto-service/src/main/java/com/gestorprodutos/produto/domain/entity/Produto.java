package com.gestorprodutos.produto.domain.entity;

import com.gestorprodutos.produto.dto.ProdutoRequestDTO;
import com.gestorprodutos.produto.exception.BusinessRuleException;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private BigDecimal preco;

    private String descricao;

    @Column(name = "categoria_id")
    private Long categoriaId;

    @Column(name = "marca_id")
    private Long marcaId;

    public Produto() {
    }

    public Produto(
            String nome,
            BigDecimal preco,
            String descricao,
            Long categoriaId,
            Long marcaId
    ) {
        this.nome = nome;
        setPreco(preco);
        this.descricao = descricao;
        this.categoriaId = categoriaId;
        this.marcaId = marcaId;
    }

    public Produto(ProdutoRequestDTO request) {
        this.nome = request.nome();
        setPreco(request.preco());
        this.descricao = request.descricao();
        this.categoriaId = request.categoriaId();
        this.marcaId = request.marcaId();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        if (preco == null || preco.signum() < 0) {
            throw new BusinessRuleException(
                    "O preço do produto não pode ser negativo"
            );
        }

        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Long getMarcaId() {
        return marcaId;
    }

    public void setMarcaId(Long marcaId) {
        this.marcaId = marcaId;
    }
}