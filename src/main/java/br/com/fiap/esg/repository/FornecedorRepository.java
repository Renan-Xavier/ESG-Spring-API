package br.com.fiap.esg.repository;

import br.com.fiap.esg.model.Fornecedor;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repositorio JPA para operacoes de banco de dados da entidade Fornecedor
@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

    // Busca um fornecedor pelo CNPJ
    Optional<Fornecedor> findByCnpj(String cnpj);
}
