package br.com.fiap.esg.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// Dados de entrada para cadastro de fornecedor
public record FornecedorRequisicaoDTO(

        @NotBlank(message = "O nome da empresa é obrigatório")
        String nomeEmpresa,

        @NotBlank(message = "O CNPJ é obrigatório")
        String cnpj,

        @NotBlank(message = "O país é obrigatório")
        String pais,

        @NotBlank(message = "O nível na cadeia é obrigatório")
        String nivelCadeia,

        @NotNull(message = "Informe se possui certificado de direitos humanos")
        Boolean certificadoDireitosHumanos
) {
}
