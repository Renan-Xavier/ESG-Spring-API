package br.com.fiap.esg.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

// Entidade que representa um fornecedor da cadeia de suprimentos no banco Oracle
@Entity
@Table(name = "tb_fornecedores")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_empresa", nullable = false, length = 150)
    private String nomeEmpresa;

    @Column(name = "cnpj", nullable = false, unique = true, length = 20)
    private String cnpj;

    @Column(name = "pais", nullable = false, length = 80)
    private String pais;

    @Column(name = "nivel_cadeia", nullable = false, length = 20)
    private String nivelCadeia;

    @Column(name = "certificado_direitos_humanos", nullable = false)
    private Boolean certificadoDireitosHumanos;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro;

    // Construtor padrao exigido pelo JPA
    public Fornecedor() {
    }

    public Fornecedor(
            String nomeEmpresa,
            String cnpj,
            String pais,
            String nivelCadeia,
            Boolean certificadoDireitosHumanos,
            LocalDate dataCadastro
    ) {
        this.nomeEmpresa = nomeEmpresa;
        this.cnpj = cnpj;
        this.pais = pais;
        this.nivelCadeia = nivelCadeia;
        this.certificadoDireitosHumanos = certificadoDireitosHumanos;
        this.dataCadastro = dataCadastro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getNivelCadeia() {
        return nivelCadeia;
    }

    public void setNivelCadeia(String nivelCadeia) {
        this.nivelCadeia = nivelCadeia;
    }

    public Boolean getCertificadoDireitosHumanos() {
        return certificadoDireitosHumanos;
    }

    public void setCertificadoDireitosHumanos(Boolean certificadoDireitosHumanos) {
        this.certificadoDireitosHumanos = certificadoDireitosHumanos;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
