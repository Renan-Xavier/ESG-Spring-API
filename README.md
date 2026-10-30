# API ESG - Transparência na Cadeia de Suprimentos

Projeto acadêmico desenvolvido em Java com Spring Boot 3 para o controle de fornecedores e registro de emissões de carbono da cadeia de suprimentos.

**Aluno:** Renan Santa Rosa Xavier  
**RM:** 566997

## Tecnologias Utilizadas
- Java 21 e Spring Boot 3
- Spring Data JPA e Banco de Dados Oracle
- Flyway para criação automática das tabelas
- Bean Validation e ProblemDetail para tratamento de erros
- Spring Security com autenticação Basic Auth
- Docker e Docker Compose

## Como Executar
Para subir a aplicação e o banco Oracle via Docker:
```bash
docker compose up --build
```

Para rodar os testes automatizados via Maven:
```bash
mvn clean test
```

## Credenciais de Acesso
- **Usuário:** `admin`
- **Senha:** `esg123`

## Endpoints Principais
- `POST /fornecedores` - Cadastra um novo fornecedor
- `GET /fornecedores` - Lista os fornecedores cadastrados
- `GET /fornecedores/{id}/conformidade` - Consulta a conformidade ESG do fornecedor
- `POST /emissoes` - Registra um novo relatório de emissão de carbono
- `GET /emissoes/{id}` - Consulta um relatório de emissão pelo ID
- `PUT /emissoes/{id}` - Atualiza os dados e o status de auditoria da emissão
- `DELETE /emissoes/{id}` - Invalida um relatório mantendo o histórico no banco
