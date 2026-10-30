package br.com.fiap.esg.dto;

import java.math.BigDecimal;

// DTO que consolida os indicadores de conformidade ESG de um fornecedor
public record ConformidadeFornecedorDTO(
        Long fornecedorId,
        String nomeEmpresa,
        String nivelCadeia,
        Boolean certificadoDireitosHumanos,
        int totalRelatoriosAtivos,
        BigDecimal totalCarbonoToneladas,
        String statusConformidadeGlobal
) {
}
