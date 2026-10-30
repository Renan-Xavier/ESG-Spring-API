package br.com.fiap.esg.repository;

import br.com.fiap.esg.model.Emissao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repositorio JPA para operacoes de banco de dados da entidade Emissao
@Repository
public interface EmissaoRepository extends JpaRepository<Emissao, Long> {

    // Lista todas as emissoes ativas de um fornecedor especifico
    List<Emissao> findByFornecedorIdAndAtivoTrue(Long fornecedorId);

    // Lista todas as emissoes ativas cadastradas no sistema
    List<Emissao> findByAtivoTrue();
}
