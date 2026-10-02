package com.gestorprodutos.produto.infrastructure.restClient;
import com.gestorprodutos.produto.dto.CategoriaResponseDTO;
import com.gestorprodutos.produto.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Component
public class CategoriaRestClient {

    private final RestClient restClient;

    public CategoriaRestClient() {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }

    public CategoriaResponseDTO buscarPorId(Long id) {
        try {
            return restClient
                    .get()
                    .uri("/categorias/{id}", id)
                    .retrieve()
                    .body(CategoriaResponseDTO.class);

        } catch (HttpClientErrorException.NotFound ex) {
            throw new ResourceNotFoundException(
                    "Categoria não encontrada com o ID: " + id
            );
        }
    }
}
