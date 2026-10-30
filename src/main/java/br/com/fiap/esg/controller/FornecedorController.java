package br.com.fiap.esg.controller;

import br.com.fiap.esg.dto.ConformidadeFornecedorDTO;
import br.com.fiap.esg.dto.FornecedorRequisicaoDTO;
import br.com.fiap.esg.dto.FornecedorRespostaDTO;
import br.com.fiap.esg.service.FornecedorService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Controller responsável pelas rotas de fornecedores
@RestController
@RequestMapping("/fornecedores")
@CrossOrigin(origins = "*")
public class FornecedorController {

    private final FornecedorService fornecedorService;

    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @PostMapping
    public ResponseEntity<FornecedorRespostaDTO> cadastrarFornecedor(
            @Valid @RequestBody FornecedorRequisicaoDTO requisicao
    ) {
        FornecedorRespostaDTO resposta = fornecedorService.cadastrarFornecedor(requisicao);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @GetMapping
    public ResponseEntity<List<FornecedorRespostaDTO>> listarFornecedores() {
        return ResponseEntity.ok(fornecedorService.listarFornecedores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorRespostaDTO> buscarFornecedorPorId(@PathVariable Long id) {
        return ResponseEntity.ok(fornecedorService.buscarFornecedorPorId(id));
    }

    // Consulta o resumo de conformidade ESG do fornecedor
    @GetMapping("/{id}/conformidade")
    public ResponseEntity<ConformidadeFornecedorDTO> consultarConformidade(@PathVariable Long id) {
        return ResponseEntity.ok(fornecedorService.avaliarConformidadeFornecedor(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FornecedorRespostaDTO> atualizarFornecedor(
            @PathVariable Long id,
            @Valid @RequestBody FornecedorRequisicaoDTO requisicao
    ) {
        return ResponseEntity.ok(fornecedorService.atualizarFornecedor(id, requisicao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirFornecedor(@PathVariable Long id) {
        fornecedorService.excluirFornecedor(id);
        return ResponseEntity.noContent().build();
    }
}
