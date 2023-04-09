package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import lombok.Builder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
@Builder
@SpringBootTest
@DisplayName("Testes do serviço de alteração do produto")
public class ProdutoAlterarServiceTest {

    @Autowired
    ProdutoAlterarService driver;

    @MockBean
    ProdutoRepository<Produto, Long> produtoRepository;

    Produto produto;

    @BeforeEach
    void setup() {
        Mockito.when(produtoRepository.find(10L))
                .thenReturn(Produto.builder()
                        .id(10L)
                        .codigoBarra("7899137500104")
                        .nome("Produto Dez")
                        .fabricante("Empresa Dez")
                        .preco(450.00)
                        .build()
                );
        produto = produtoRepository.find(10L);
    }

    @Test
    @DisplayName("Quando um novo nome válido for fornecido para o produto")
    void quandoNovoNomeValido() {
        // Arrange
        produto.setNome("Produto Dez Atualizado");
        Mockito.when(produtoRepository.update(produto))
                .thenReturn(Produto.builder()
                        .id(10L)
                        .codigoBarra("7899137500104")
                        .nome("Produto Dez Atualizado")
                        .fabricante("Empresa Dez")
                        .preco(450.00)
                        .build()
                );
        // Act
        Produto resultado = driver.alterar(produto);

        // Assert
        assertEquals("Produto Dez Atualizado", resultado.getNome());
    }

    @Test
    @DisplayName("Quando um novo nome inválido for fornecido para o produto")
    void quandoNovoNomeInvalido() {
        // Arrange
        produto.setNome(null);
        String nomeOriginal = produto.getNome();

        // Act
        driver.alterar(produto);

        // Assert
        assertEquals(nomeOriginal, produto.getNome());
    }

    @Test
    @DisplayName("Quando um novo preço válido for fornecido para o produto")
    void quandoNovoPrecoValido() {
        // Arrange
        produto.setPreco(500.00);
        Mockito.when(produtoRepository.update(produto))
                .thenReturn(Produto.builder()
                        .id(10L)
                        .codigoBarra("7899137500104")
                        .nome("Produto Dez")
                        .fabricante("Empresa Dez")
                        .preco(500.00)
                        .build()
                );

        // Act
        Produto resultado = driver.alterar(produto);

        // Assert
        assertEquals(500.00, resultado.getPreco());
    }

    @Test
    @DisplayName("Quando o valor do produto for menor ou igual a zero")
    void quandoValorMenorIgualZero() {
        // Arrange
        double precoInvalido = -10.0;
        produto.setPreco(precoInvalido);

        // Act
        driver.alterar(produto);

        // Assert
        assertEquals(0.0, produto.getPreco());
    }

    @Test
    @DisplayName("Quando um novo código de barras válido for fornecido para o produto")
    void quandoNovoCodigoBarraValido() {
        // Arrange
        produto.setCodigoBarra("7899137500104");
        Mockito.when(produtoRepository.update(produto))
                .thenReturn(Produto.builder()
                        .id(10L)
                        .codigoBarra("7899137500203")
                        .nome("Produto Dez")
                        .fabricante("Empresa Dez")
                        .preco(500.00)
                        .build()
                );

        // Act
        Produto resultado = driver.alterar(produto);

        // Assert
        assertEquals("7899137500203", resultado.getCodigoBarra());
    }

    @Test
    @DisplayName("Quando um novo código de barras inválido for fornecido para o produto")
    void quandoNovoCodigoBarraInvalido() {
        // Arrange
        produto.setCodigoBarra("123456789");
        Mockito.when(produtoRepository.update(produto)).thenThrow(new IllegalArgumentException("O código de barras fornecido é inválido"));
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            driver.alterar(produto);
        });
        assertEquals("O código de barras fornecido é inválido", exception.getMessage());
    }

}