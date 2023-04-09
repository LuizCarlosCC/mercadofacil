package com.ufcg.psoft.mercadofacil.service;

public class ProdutoValidarCodigoDeBarrasService {

    private static final String CODIGO_BARRAS_PREFIXO = "78991375";
    private static final int CODIGO_BARRAS_TAMANHO = 13;

    public static boolean validarCodigoDeBarras(String codigoBarra, Long id) {
        if (codigoBarra == null || codigoBarra.length() != CODIGO_BARRAS_TAMANHO) {
            return false;
        }
        String prefixoEsperado = CODIGO_BARRAS_PREFIXO + String.format("%04d", id);
        if (!codigoBarra.startsWith(prefixoEsperado)) {
            return false;
        }
        int soma = 0;
        for (int i = 0; i < CODIGO_BARRAS_TAMANHO - 1; i++) {
            int digito = Character.getNumericValue(codigoBarra.charAt(i));
            if (i % 2 == 0) {
                soma += digito * 3;
            } else {
                soma += digito;
            }
        }
        int digitoVerificadorEsperado = 10 - (soma % 10);
        if (digitoVerificadorEsperado == 10) {
            digitoVerificadorEsperado = 0;
        }
        int digitoVerificadorReal = Character.getNumericValue(codigoBarra.charAt(CODIGO_BARRAS_TAMANHO - 1));
        return digitoVerificadorReal == digitoVerificadorEsperado;
    }
}
