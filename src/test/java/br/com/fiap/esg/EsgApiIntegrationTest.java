package br.com.fiap.esg;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EsgApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveBloquearRequisicaoSemAutenticacao() throws Exception {
        mockMvc.perform(get("/fornecedores"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deveExecutarFluxoCompletoDeFornecedorEEmissao() throws Exception {
        String jsonFornecedor = """
                {
                    "nomeEmpresa": "BioEnergia Sul S.A.",
                    "cnpj": "11.222.333/0001-44",
                    "pais": "Brasil",
                    "nivelCadeia": "TIER_1",
                    "certificadoDireitosHumanos": true
                }
                """;

        mockMvc.perform(post("/fornecedores")
                        .with(httpBasic("admin", "esg123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonFornecedor))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nomeEmpresa").value("BioEnergia Sul S.A."));

        mockMvc.perform(get("/fornecedores")
                        .with(httpBasic("admin", "esg123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        String jsonEmissao = """
                {
                    "fornecedorId": 1,
                    "categoriaAmbito3": "Categoria 1 - Bens e Servicos Adquiridos",
                    "quantidadeCarbono": 85.40,
                    "tipoDado": "PRIMARIO",
                    "dataRelatorio": "2026-05-10"
                }
                """;

        mockMvc.perform(post("/emissoes")
                        .with(httpBasic("admin", "esg123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonEmissao))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusAuditoria").value("PENDENTE"))
                .andExpect(jsonPath("$.quantidadeCarbono").value(85.40));

        mockMvc.perform(get("/emissoes/1")
                        .with(httpBasic("admin", "esg123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        String jsonAtualizacao = """
                {
                    "categoriaAmbito3": "Categoria 1 - Bens e Servicos Adquiridos",
                    "quantidadeCarbono": 310.00,
                    "tipoDado": "PRIMARIO",
                    "statusAuditoria": "AUDITADO"
                }
                """;

        mockMvc.perform(put("/emissoes/2")
                        .with(httpBasic("admin", "esg123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAtualizacao))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusAuditoria").value("AUDITADO"));

        mockMvc.perform(get("/fornecedores/1/conformidade")
                        .with(httpBasic("admin", "esg123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusConformidadeGlobal").value("CONFORME_AUDITADO"));

        mockMvc.perform(delete("/emissoes/2")
                        .with(httpBasic("admin", "esg123")))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveRetornarProblemDetailQuandoValidacaoFalhar() throws Exception {
        String jsonEmissaoInvalida = """
                {
                    "fornecedorId": 1,
                    "categoriaAmbito3": "",
                    "quantidadeCarbono": -50.00,
                    "tipoDado": "PRIMARIO",
                    "dataRelatorio": "2026-05-10"
                }
                """;

        mockMvc.perform(post("/emissoes")
                        .with(httpBasic("admin", "esg123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonEmissaoInvalida))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Erro de validação nos dados enviados"))
                .andExpect(jsonPath("$.errosCampos.quantidadeCarbono").exists())
                .andExpect(jsonPath("$.errosCampos.categoriaAmbito3").exists());
    }
}
