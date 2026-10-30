package br.com.fiap.esg.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

// Entidade que representa um relatorio de emissao de carbono de Ambito 3
@Entity
@Table(name = "tb_emissoes")
public class Emissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento com o fornecedor responsavel pela emissao
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private Fornecedor fornecedor;

    @Column(name = "categoria_ambito3", nullable = false, length = 100)
    private String categoriaAmbito3;

    @Column(name = "quantidade_carbono", nullable = false, precision = 12, scale = 2)
    private BigDecimal quantidadeCarbono;

    @Column(name = "tipo_dado", nullable = false, length = 30)
    private String tipoDado;

    @Column(name = "status_auditoria", nullable = false, length = 30)
    private String statusAuditoria;

    @Column(name = "data_relatorio", nullable = false)
    private LocalDate dataRelatorio;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    // Construtor padrao exigido pelo JPA
    public Emissao() {
    }

    public Emissao(
            Fornecedor fornecedor,
            String categoriaAmbito3,
            BigDecimal quantidadeCarbono,
            String tipoDado,
            String statusAuditoria,
            LocalDate dataRelatorio,
            Boolean ativo
    ) {
        this.fornecedor = fornecedor;
        this.categoriaAmbito3 = categoriaAmbito3;
        this.quantidadeCarbono = quantidadeCarbono;
        this.tipoDado = tipoDado;
        this.statusAuditoria = statusAuditoria;
        this.dataRelatorio = dataRelatorio;
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getCategoriaAmbito3() {
        return categoriaAmbito3;
    }

    public void setCategoriaAmbito3(String categoriaAmbito3) {
        this.categoriaAmbito3 = categoriaAmbito3;
    }

    public BigDecimal getQuantidadeCarbono() {
        return quantidadeCarbono;
    }

    public void setQuantidadeCarbono(BigDecimal quantidadeCarbono) {
        this.quantidadeCarbono = quantidadeCarbono;
    }

    public String getTipoDado() {
        return tipoDado;
    }

    public void setTipoDado(String tipoDado) {
        this.tipoDado = tipoDado;
    }

    public String getStatusAuditoria() {
        return statusAuditoria;
    }

    public void setStatusAuditoria(String statusAuditoria) {
        this.statusAuditoria = statusAuditoria;
    }

    public LocalDate getDataRelatorio() {
        return dataRelatorio;
    }

    public void setDataRelatorio(LocalDate dataRelatorio) {
        this.dataRelatorio = dataRelatorio;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
