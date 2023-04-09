package com.ufcg.psoft.mercadofacil.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.mercadofacil.controller.ProdutoV1Controller;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes do controlador de Produtos")
public class ProdutoV1ControllerTests{

        @Autowired
        MockMvc driver;

        @Autowired
        ProdutoRepository<Produto, Long> produtoRepository;

        ObjectMapper objectMapper = new ObjectMapper();

        Produto produto;

        @BeforeEach
        void setup() {
                produto = produtoRepository.find(10L);
        }

        @Test
        @DisplayName("Quando alteramos o nome do produto com dados válidos")
        void quandoAlteramosNomeDoPodutoValido() throws Exception {
                // Arrange
                produto.setNome("Produto Dez Alterado");

                // Act
                String responseJsonString = driver.perform(put("/v1/produtos/" + produto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produto)))
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                Produto resultado = objectMapper.readValue(responseJsonString, Produto.ProdutoBuilder.class).build();

                // Assert
                 assertEquals(resultado.getNome(), "Produto Dez Alterado");
        }
}
