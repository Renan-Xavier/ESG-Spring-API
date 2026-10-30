package br.com.fiap.esg.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

// Dados de entrada para lançamento de emissão de carbono
public record EmissaoRequisicaoDTO(

        @NotNull(message = "O ID do fornecedor é obrigatório")
        Long fornecedorId,

        @NotBlank(message = "A categoria de emissão é obrigatória")
        String categoriaAmbito3,

        @NotNull(message = "A quantidade de carbono é obrigatória")
        @Positive(message = "A quantidade de carbono deve ser maior que zero")
        BigDecimal quantidadeCarbono,

        @NotBlank(message = "O tipo de dado é obrigatório")
        String tipoDado,

        @NotNull(message = "A data do relatório é obrigatória")
        @PastOrPresent(message = "A data do relatório não pode ser futura")
        LocalDate dataRelatorio
) {
}
