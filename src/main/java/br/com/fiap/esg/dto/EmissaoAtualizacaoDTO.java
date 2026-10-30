package br.com.fiap.esg.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

// Dados para atualização e auditoria de uma emissão
public record EmissaoAtualizacaoDTO(

        @NotBlank(message = "A categoria de emissão é obrigatória")
        String categoriaAmbito3,

        @NotNull(message = "A quantidade de carbono é obrigatória")
        @Positive(message = "A quantidade de carbono deve ser maior que zero")
        BigDecimal quantidadeCarbono,

        @NotBlank(message = "O tipo de dado é obrigatório")
        String tipoDado,

        @NotBlank(message = "O status da auditoria é obrigatório")
        String statusAuditoria
) {
}
