-- Insercao de fornecedor inicial para testes da API
INSERT INTO tb_fornecedores (nome_empresa, cnpj, pais, nivel_cadeia, certificado_direitos_humanos, data_cadastro)
VALUES ('EcoLogistica Global S.A.', '12.345.678/0001-90', 'Brasil', 'TIER_1', 1, CURRENT_DATE);

INSERT INTO tb_fornecedores (nome_empresa, cnpj, pais, nivel_cadeia, certificado_direitos_humanos, data_cadastro)
VALUES ('Metalurgica Verde Ltda.', '98.765.432/0001-10', 'Brasil', 'TIER_2', 1, CURRENT_DATE);

-- Insercao de relatorios de emissao de Ambito 3 iniciais
INSERT INTO tb_emissoes (fornecedor_id, categoria_ambito3, quantidade_carbono, tipo_dado, status_auditoria, data_relatorio, ativo)
VALUES (1, 'Categoria 4 - Transporte e Distribuicao', 145.50, 'PRIMARIO', 'AUDITADO', CURRENT_DATE, 1);

INSERT INTO tb_emissoes (fornecedor_id, categoria_ambito3, quantidade_carbono, tipo_dado, status_auditoria, data_relatorio, ativo)
VALUES (2, 'Categoria 1 - Bens e Servicos Adquiridos', 320.75, 'PRIMARIO', 'PENDENTE', CURRENT_DATE, 1);
