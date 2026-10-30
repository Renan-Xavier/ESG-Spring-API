package br.com.fiap.esg.dto;

import br.com.fiap.esg.model.Fornecedor;
import java.time.LocalDate;

// DTO de saida com os dados cadastrados do fornecedor
public record FornecedorRespostaDTO(
        Long id,
        String nomeEmpresa,
        String cnpj,
        String pais,
        String nivelCadeia,
        Boolean certificadoDireitosHumanos,
        LocalDate dataCadastro
) {
    // Converte a entidade Fornecedor para DTO de resposta
    public static FornecedorRespostaDTO deEntidade(Fornecedor fornecedor) {
        return new FornecedorRespostaDTO(
                fornecedor.getId(),
                fornecedor.getNomeEmpresa(),
                fornecedor.getCnpj(),
                fornecedor.getPais(),
                fornecedor.getNivelCadeia(),
                fornecedor.getCertificadoDireitosHumanos(),
                fornecedor.getDataCadastro()
        );
    }
}
