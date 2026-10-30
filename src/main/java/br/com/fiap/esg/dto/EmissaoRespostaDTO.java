package br.com.fiap.esg.dto;

import br.com.fiap.esg.model.Emissao;
import java.math.BigDecimal;
import java.time.LocalDate;

// DTO de saida com os detalhes do relatorio de emissao
public record EmissaoRespostaDTO(
        Long id,
        Long fornecedorId,
        String nomeFornecedor,
        String categoriaAmbito3,
        BigDecimal quantidadeCarbono,
        String tipoDado,
        String statusAuditoria,
        LocalDate dataRelatorio,
        Boolean ativo
) {
    // Converte a entidade Emissao para DTO de resposta
    public static EmissaoRespostaDTO deEntidade(Emissao emissao) {
        return new EmissaoRespostaDTO(
                emissao.getId(),
                emissao.getFornecedor().getId(),
                emissao.getFornecedor().getNomeEmpresa(),
                emissao.getCategoriaAmbito3(),
                emissao.getQuantidadeCarbono(),
                emissao.getTipoDado(),
                emissao.getStatusAuditoria(),
                emissao.getDataRelatorio(),
                emissao.getAtivo()
        );
    }
}
