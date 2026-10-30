package br.com.fiap.esg.service;

import br.com.fiap.esg.dto.EmissaoAtualizacaoDTO;
import br.com.fiap.esg.dto.EmissaoRequisicaoDTO;
import br.com.fiap.esg.dto.EmissaoRespostaDTO;
import br.com.fiap.esg.excecao.RecursoNaoEncontradoException;
import br.com.fiap.esg.model.Emissao;
import br.com.fiap.esg.model.Fornecedor;
import br.com.fiap.esg.repository.EmissaoRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Regras de negócio para gerenciamento de emissões de carbono
@Service
public class EmissaoService {

    private final EmissaoRepository emissaoRepository;
    private final FornecedorService fornecedorService;

    public EmissaoService(
            EmissaoRepository emissaoRepository,
            FornecedorService fornecedorService
    ) {
        this.emissaoRepository = emissaoRepository;
        this.fornecedorService = fornecedorService;
    }

    @Transactional
    public EmissaoRespostaDTO salvarEmissao(EmissaoRequisicaoDTO dados) {
        Fornecedor fornecedor = fornecedorService.buscarEntidadePorId(dados.fornecedorId());

        Emissao novaEmissao = new Emissao(
                fornecedor,
                dados.categoriaAmbito3(),
                dados.quantidadeCarbono(),
                dados.tipoDado().toUpperCase(),
                "PENDENTE",
                dados.dataRelatorio(),
                true
        );

        Emissao emissaoSalva = emissaoRepository.save(novaEmissao);
        return EmissaoRespostaDTO.deEntidade(emissaoSalva);
    }

    @Transactional(readOnly = true)
    public List<EmissaoRespostaDTO> listarEmissoes() {
        return emissaoRepository.findByAtivoTrue()
                .stream()
                .map(EmissaoRespostaDTO::deEntidade)
                .toList();
    }

    @Transactional(readOnly = true)
    public EmissaoRespostaDTO buscarEmissaoPorId(Long id) {
        Emissao emissao = buscarEntidadePorId(id);
        return EmissaoRespostaDTO.deEntidade(emissao);
    }

    @Transactional
    public EmissaoRespostaDTO atualizarEmissao(Long id, EmissaoAtualizacaoDTO dados) {
        Emissao emissao = buscarEntidadePorId(id);
        emissao.setCategoriaAmbito3(dados.categoriaAmbito3());
        emissao.setQuantidadeCarbono(dados.quantidadeCarbono());
        emissao.setTipoDado(dados.tipoDado().toUpperCase());
        emissao.setStatusAuditoria(dados.statusAuditoria().toUpperCase());

        Emissao atualizada = emissaoRepository.save(emissao);
        return EmissaoRespostaDTO.deEntidade(atualizada);
    }

    // Marca a emissão como inativa para manter o histórico de auditoria
    @Transactional
    public void invalidarEmissao(Long id) {
        Emissao emissao = buscarEntidadePorId(id);
        emissao.setAtivo(false);
        emissao.setStatusAuditoria("INVALIDADO");
        emissaoRepository.save(emissao);
    }

    private Emissao buscarEntidadePorId(Long id) {
        return emissaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Relatório de emissão não encontrado com o ID: " + id
                ));
    }
}
