INSERT INTO TB_CLIENTE (cpf, nome, classe_bonus) VALUES ('11111111111', 'Joana', 'Bronze');
INSERT INTO TB_CLIENTE (cpf, nome, classe_bonus) VALUES ('22222222222', 'Carlos', 'Prata');
INSERT INTO TB_CLIENTE (cpf, nome, classe_bonus) VALUES ('33333333333', 'Marcos', 'Ouro');
INSERT INTO TB_PRODUTO (descricao, preco, dt_validade) VALUES ('Enxaguante Bucal Sem Álcool Listerine Cool Mint 1L',29.99,'2026-06-30');
INSERT INTO TB_PRODUTO (descricao, preco, dt_validade) VALUES ('Dipirona Sódica 500mg/ml Solução Gotas 20ml',4.29,'2024-06-30');
INSERT INTO TB_PRODUTO (descricao, preco, dt_validade) VALUES ('Creme Hidratante Corporal Pele Seca 453g',69.50,'2023-12-31');
INSERT INTO TB_CARRINHO_COMPRA(cliente_id, total, desconto, total_com_desconto, id) VALUES (3, 9.0, 0.0, 9.0, default)
INSERT INTO TB_ITEM_COMPRA (carrinho_compra_id, produto_id, quantidade, sub_total, id) VALUES (1, 2, 2, 9.0, default)