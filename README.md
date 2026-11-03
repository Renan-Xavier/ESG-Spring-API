# API ESG — Transparência e Conformidade na Cadeia de Suprimentos

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Oracle](https://img.shields.io/badge/Oracle_SQL-Database-F80000?style=for-the-badge&logo=oracle&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-Migrations-CC0200?style=for-the-badge&logo=flyway&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=for-the-badge&logo=docker&logoColor=white)
[![Portfólio](https://img.shields.io/badge/Portfólio-renan--xavier.vercel.app-000000?style=for-the-badge&logo=vercel&logoColor=white)](https://renan-xavier.vercel.app)

**Autor:** Renan Santa Rosa Xavier — RM566997 | 🌐 **Portfólio:** [renan-xavier.vercel.app](https://renan-xavier.vercel.app)

---

## Visão Geral do Projeto

API RESTful desenvolvida em **Java 21 com Spring Boot 3** para o controle de fornecedores, rastreamento de **emissões de carbono de Escopo 3** e diagnóstico automatizado de **conformidade ESG** na cadeia de suprimentos.

![Painel Simulador ESG Integrado](docs/img/painel_simulador.png)

---

## Arquitetura da Solução

```mermaid
flowchart LR
    Client["Cliente REST / Simulador ESG\n(HTTP Basic Auth)"] --> Security["Spring Security\nConfiguracaoSeguranca"]
    Security --> Ctrl["Controllers REST\nFornecedorController / EmissaoController"]
    Ctrl --> Svc["Service Layer\nRegras ESG & Cálculo de Conformidade"]
    Svc --> Repo["Spring Data JPA\nFornecedorRepository / EmissaoRepository"]
    Repo --> DB[("Oracle Database\nMigrations V1 & V2 via Flyway")]
```

### Destaques Técnicos e Apresentação do Projeto
| Arquitetura e Contexto ESG | Containerização (Docker & Compose) | Testes Automatizados da API |
| :---: | :---: | :---: |
| ![Slide 1](docs/img/slide_p01.png) | ![Slide 2](docs/img/slide_p02.png) | ![Slide 3](docs/img/slide_p03.png) |

- **Java 21 & Spring Boot 3:** Arquitetura em camadas (`controller`, `service`, `repository`, `dto`, `model`, `excecao`, `seguranca`).
- **Migrações Versionadas com Flyway:** Criação automática das tabelas (`V1__criar_tabelas_esg.sql`) e carga inicial de fornecedores e emissões (`V2__inserir_dados_iniciais.sql`).
- **Validação e Tratamento Global de Erros:** Uso de Bean Validation (`@Valid`) e respostas padronizadas com `ProblemDetail` (`TratadorGlobalExcecoes`).
- **Segurança:** Autenticação HTTP Basic configurada via `Spring Security`.
- **Containerização Completa:** `Dockerfile` multi-stage e `docker-compose.yml` orquestrando a API e o banco de dados.

---

## Endpoints Principais

| Método | Rota | Descrição |
| :---: | :--- | :--- |
| `GET` | `/fornecedores` | Lista todos os fornecedores cadastrados na cadeia de suprimentos |
| `POST` | `/fornecedores` | Cadastra um novo fornecedor com meta anual de emissões (`tCO2e`) |
| `GET` | `/fornecedores/{id}/conformidade` | Calcula o diagnóstico de conformidade ESG comparando emissões auditadas com a meta |
| `POST` | `/emissoes` | Registra um novo relatório de emissão de carbono de Escopo 3 |
| `GET` | `/emissoes/{id}` | Consulta os detalhes de um relatório de emissão específico |
| `PUT` | `/emissoes/{id}` | Atualiza dados e aprova a auditoria (`PENDENTE` → `AUDITADO`) |
| `DELETE` | `/emissoes/{id}` | Invalida/remove um relatório de emissão |

---

## Como Executar

### 1. Subir via Docker Compose
```bash
docker compose up --build
```

### 2. Executar Testes de Integração via Maven
```bash
mvn clean test
```

### Credenciais de Acesso (HTTP Basic Auth)
- **Usuário:** `admin`
- **Senha:** `esg123`
