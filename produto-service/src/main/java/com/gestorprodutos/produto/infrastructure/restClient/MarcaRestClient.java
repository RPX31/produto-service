package com.gestorprodutos.produto.infrastructure.restClient;

import com.gestorprodutos.produto.dto.MarcaResponseDTO;
import com.gestorprodutos.produto.exception.ResourceNotFoundException;
import com.gestorprodutos.produto.exception.ResourceinternalerrorException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;


@Component
public class MarcaRestClient {


    private final RestClient restClient;

    public MarcaRestClient() {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8083")
                .build();
    }


    public MarcaResponseDTO buscarPorId(Long id) {
        try {
            return restClient
                    .get()
                    .uri("/marcas/{id}", id)
                    .retrieve()
                    .body(MarcaResponseDTO.class);

        } catch (HttpClientErrorException.NotFound ex) {
            throw new ResourceNotFoundException(
                    "Marca não encontrada com o ID: " + id
            );
        }
        catch (ResourceAccessException ex) {
            throw new ResourceinternalerrorException(
                    "Impossivel conectar-se com a marca no momento "
            );
        }
    }
}