package com.ufcg.psoft.mercadofacil.repository;

import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VolatilLoteRepositoryTest {

    @Autowired
    LoteRepository<Lote, Long> driver;

    Lote lote;
    Lote resultado;
    Produto produto;

    @BeforeEach
    void setup() {
        produto = Produto.builder()
                .id(1L)
                .nome("Produto Base")
                .codigoBarra("123456789")
                .fabricante("Fabricante Base")
                .preco(125.36)
                .build();
        lote = Lote.builder()
                .id(1L)
                .numeroDeItens(100)
                .produto(produto)
                .build();
    }
    @AfterEach
    void tearDown() {
        produto = null;
        driver.deleteAll();
    }

    @Test
    @DisplayName("Adicionar o primeiro Lote no repositorio de dados")
    void salvarPrimeiroLote() {
        resultado = driver.save(lote);

        assertEquals(driver.findAll().size(),1);
        assertEquals(resultado.getId().longValue(), lote.getId().longValue());
        assertEquals(resultado.getProduto(), produto);
    }

    @Test
    @DisplayName("Adicionar o segundo Lote (ou posterior) no repositorio de dados")
    void salvarSegundoLoteOuPosterior() {
        Produto produtoExtra = Produto.builder()
                .id(2L)
                .nome("Produto Extra")
                .codigoBarra("987654321")
                .fabricante("Fabricante Extra")
                .preco(125.36)
                .build();
        Lote loteExtra = Lote.builder()
                .id(2L)
                .numeroDeItens(100)
                .produto(produtoExtra)
                .build();
        driver.save(lote);

        resultado = driver.save(loteExtra);

        assertEquals(driver.findAll().size(),2);
        assertEquals(resultado.getId().longValue(), loteExtra.getId().longValue());
        assertEquals(resultado.getProduto(), produtoExtra);

    }

    @Test
    @DisplayName("Excluir um lote existente no repositório")
    void excluirLoteExistente() {
        // Salva o lote no repositório
        driver.save(lote);

        // Exclui o lote do repositório
        driver.delete(lote);

        // Verifica se o lote foi excluído corretamente
        assertEquals(0, driver.findAll().size());
    }

    @Test
    @DisplayName("Atualizar um lote existente no repositório")
    void atualizarLoteExistente() {
        // Salva o lote no repositório
        driver.save(lote);

        // Atualiza o número de itens do lote para 200
        lote.setNumeroDeItens(100);
        Lote loteAtualizado = driver.update(lote);

        // Verifica se o lote foi atualizado corretamente
        assertEquals(1, driver.findAll().size());
        assertEquals(100, loteAtualizado.getNumeroDeItens());
    }

    @Test
    @DisplayName("Excluir um lote que não existe no repositório")
    void excluirLoteInexistente() {
        // Cria um novo lote com um ID diferente do lote anterior
        Lote novoLote = Lote.builder()
                .id(2L)
                .numeroDeItens(50)
                .produto(produto)
                .build();

        // Tenta excluir o lote que não existe no repositório
        driver.delete(novoLote);

        // Verifica se o lote não foi excluído
        assertEquals(0, driver.findAll().size());
    }

    @Test
    @DisplayName("Deletar todos os lotes existentes")
    void deletarTodosLotes() {
        Produto produtoExtra = Produto.builder()
                .id(2L)
                .nome("Produto Extra")
                .codigoBarra("987654321")
                .fabricante("Fabricante Extra")
                .preco(125.36)
                .build();
        Lote loteExtra = Lote.builder()
                .id(2L)
                .numeroDeItens(100)
                .produto(produtoExtra)
                .build();
        driver.save(lote);
        driver.save(loteExtra);
        driver.deleteAll();
        assertEquals(driver.findAll().size(), 0);
    }

    @Test
    @DisplayName("Tentar atualizar um lote que não existe")
    void atualizarLoteInexistente() {
        Produto produtoExtra = Produto.builder()
                .id(2L)
                .nome("Produto Extra")
                .codigoBarra("987654321")
                .fabricante("Fabricante Extra")
                .preco(125.36)
                .build();
        Lote loteExtra = Lote.builder()
                .id(2L)
                .numeroDeItens(100)
                .produto(produtoExtra)
                .build();
        resultado = driver.update(loteExtra);
        assertNull(resultado);
    }


}
