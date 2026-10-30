package br.com.fiap.esg.service;

import br.com.fiap.esg.dto.ConformidadeFornecedorDTO;
import br.com.fiap.esg.dto.FornecedorRequisicaoDTO;
import br.com.fiap.esg.dto.FornecedorRespostaDTO;
import br.com.fiap.esg.excecao.RecursoNaoEncontradoException;
import br.com.fiap.esg.excecao.RegraDeNegocioException;
import br.com.fiap.esg.model.Emissao;
import br.com.fiap.esg.model.Fornecedor;
import br.com.fiap.esg.repository.EmissaoRepository;
import br.com.fiap.esg.repository.FornecedorRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Regras de negócio para gerenciamento de fornecedores
@Service
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;
    private final EmissaoRepository emissaoRepository;

    public FornecedorService(
            FornecedorRepository fornecedorRepository,
            EmissaoRepository emissaoRepository
    ) {
        this.fornecedorRepository = fornecedorRepository;
        this.emissaoRepository = emissaoRepository;
    }

    @Transactional
    public FornecedorRespostaDTO cadastrarFornecedor(FornecedorRequisicaoDTO dados) {
        fornecedorRepository.findByCnpj(dados.cnpj()).ifPresent(existente -> {
            throw new RegraDeNegocioException("Já existe um fornecedor cadastrado com este CNPJ");
        });

        Fornecedor fornecedor = new Fornecedor(
                dados.nomeEmpresa(),
                dados.cnpj(),
                dados.pais(),
                dados.nivelCadeia(),
                dados.certificadoDireitosHumanos(),
                LocalDate.now()
        );

        Fornecedor salvo = fornecedorRepository.save(fornecedor);
        return FornecedorRespostaDTO.deEntidade(salvo);
    }

    @Transactional(readOnly = true)
    public List<FornecedorRespostaDTO> listarFornecedores() {
        return fornecedorRepository.findAll()
                .stream()
                .map(FornecedorRespostaDTO::deEntidade)
                .toList();
    }

    @Transactional(readOnly = true)
    public FornecedorRespostaDTO buscarFornecedorPorId(Long id) {
        Fornecedor fornecedor = buscarEntidadePorId(id);
        return FornecedorRespostaDTO.deEntidade(fornecedor);
    }

    @Transactional
    public FornecedorRespostaDTO atualizarFornecedor(Long id, FornecedorRequisicaoDTO dados) {
        Fornecedor fornecedor = buscarEntidadePorId(id);
        fornecedor.setNomeEmpresa(dados.nomeEmpresa());
        fornecedor.setCnpj(dados.cnpj());
        fornecedor.setPais(dados.pais());
        fornecedor.setNivelCadeia(dados.nivelCadeia());
        fornecedor.setCertificadoDireitosHumanos(dados.certificadoDireitosHumanos());

        Fornecedor atualizado = fornecedorRepository.save(fornecedor);
        return FornecedorRespostaDTO.deEntidade(atualizado);
    }

    @Transactional
    public void excluirFornecedor(Long id) {
        Fornecedor fornecedor = buscarEntidadePorId(id);
        fornecedorRepository.delete(fornecedor);
    }

    // Soma as emissões ativas e define o status de conformidade do fornecedor
    @Transactional(readOnly = true)
    public ConformidadeFornecedorDTO avaliarConformidadeFornecedor(Long id) {
        Fornecedor fornecedor = buscarEntidadePorId(id);
        List<Emissao> emissoesAtivas = emissaoRepository.findByFornecedorIdAndAtivoTrue(id);

        BigDecimal totalCarbono = emissoesAtivas.stream()
                .map(Emissao::getQuantidadeCarbono)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        boolean possuiRelatorioAuditado = emissoesAtivas.stream()
                .anyMatch(e -> "AUDITADO".equalsIgnoreCase(e.getStatusAuditoria()));

        String statusConformidade;
        if (!Boolean.TRUE.equals(fornecedor.getCertificadoDireitosHumanos())) {
            statusConformidade = "NAO_CONFORME_DIREITOS_HUMANOS";
        } else if (emissoesAtivas.isEmpty()) {
            statusConformidade = "PENDENTE_REPORTE_AMBITO3";
        } else if (possuiRelatorioAuditado) {
            statusConformidade = "CONFORME_AUDITADO";
        } else {
            statusConformidade = "EM_ANALISE_AUDITORIA";
        }

        return new ConformidadeFornecedorDTO(
                fornecedor.getId(),
                fornecedor.getNomeEmpresa(),
                fornecedor.getNivelCadeia(),
                fornecedor.getCertificadoDireitosHumanos(),
                emissoesAtivas.size(),
                totalCarbono,
                statusConformidade
        );
    }

    public Fornecedor buscarEntidadePorId(Long id) {
        return fornecedorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Fornecedor não encontrado com o ID: " + id
                ));
    }
}
