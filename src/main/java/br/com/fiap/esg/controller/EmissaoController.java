package br.com.fiap.esg.controller;

import br.com.fiap.esg.dto.EmissaoAtualizacaoDTO;
import br.com.fiap.esg.dto.EmissaoRequisicaoDTO;
import br.com.fiap.esg.dto.EmissaoRespostaDTO;
import br.com.fiap.esg.service.EmissaoService;
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

// Controller responsável pelo cadastro e auditoria de emissões de carbono
@RestController
@RequestMapping("/emissoes")
@CrossOrigin(origins = "*")
public class EmissaoController {

    private final EmissaoService emissaoService;

    public EmissaoController(EmissaoService emissaoService) {
        this.emissaoService = emissaoService;
    }

    @PostMapping
    public ResponseEntity<EmissaoRespostaDTO> salvarEmissao(
            @Valid @RequestBody EmissaoRequisicaoDTO requisicao
    ) {
        EmissaoRespostaDTO resposta = emissaoService.salvarEmissao(requisicao);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @GetMapping
    public ResponseEntity<List<EmissaoRespostaDTO>> listarEmissoes() {
        return ResponseEntity.ok(emissaoService.listarEmissoes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmissaoRespostaDTO> buscarEmissaoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(emissaoService.buscarEmissaoPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmissaoRespostaDTO> atualizarEmissao(
            @PathVariable Long id,
            @Valid @RequestBody EmissaoAtualizacaoDTO requisicao
    ) {
        return ResponseEntity.ok(emissaoService.atualizarEmissao(id, requisicao));
    }

    // Invalida o relatório sem apagar o registro do banco para manter histórico
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> invalidarEmissao(@PathVariable Long id) {
        emissaoService.invalidarEmissao(id);
        return ResponseEntity.noContent().build();
    }
}
