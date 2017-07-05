-- Pagina Personalizada OI

-- Module insert
INSERT INTO BLC_ADMIN_MODULE (ADMIN_MODULE_ID, NAME, MODULE_KEY, ICON, DISPLAY_ORDER) VALUES (1, 'Regionalização', 'Regionalização','blc-icon-globe', 1);

-- Session Module insert
-- INSERT INTO BLC_ADMIN_SECTION (ADMIN_SECTION_ID, DISPLAY_ORDER, ADMIN_MODULE_ID, NAME, SECTION_KEY, URL, CEILING_ENTITY) VALUES (1, 1, 1, 'Região', 'Região', '/regiao', 'com.oi.entidades.Regiao');

INSERT INTO BLC_ADMIN_SECTION (ADMIN_SECTION_ID, CEILING_ENTITY, DISPLAY_CONTROLLER, DISPLAY_ORDER, NAME, SECTION_KEY, URL, USE_DEFAULT_HANDLER, ADMIN_MODULE_ID ) VALUES (1,'com.oi.entidades.Regiao', NULL, 1,'Região', 'REGIAO', '/regiao', NULL, 1);

INSERT INTO BLC_ADMIN_SECTION (ADMIN_SECTION_ID, CEILING_ENTITY, DISPLAY_CONTROLLER, DISPLAY_ORDER, NAME, SECTION_KEY, URL, USE_DEFAULT_HANDLER, ADMIN_MODULE_ID ) VALUES (2,'com.oi.entidades.Cidade', NULL, 1,'Cidade', 'CIDADE', '/cidade', NULL, 1);

-- PERMISSAO REGIAO
INSERT INTO BLC_ADMIN_PERMISSION (ADMIN_PERMISSION_ID, DESCRIPTION, NAME, PERMISSION_TYPE, IS_FRIENDLY) VALUES (1, 'Todas as Permissoes', 'PERMISSION_ALL_REGIOES', 'ALL', false);

-- Admin permission entity
INSERT INTO BLC_ADMIN_PERMISSION_ENTITY (ADMIN_PERMISSION_ENTITY_ID, CEILING_ENTITY, ADMIN_PERMISSION_ID) VALUES (1, 'com.oi.entidades.Regiao', 1);
INSERT INTO BLC_ADMIN_PERMISSION_ENTITY (ADMIN_PERMISSION_ENTITY_ID, CEILING_ENTITY, ADMIN_PERMISSION_ID) VALUES (2, 'com.oi.entidades.Cidade', 1);
INSERT INTO BLC_ADMIN_PERMISSION_ENTITY (ADMIN_PERMISSION_ENTITY_ID, CEILING_ENTITY, ADMIN_PERMISSION_ID) VALUES (3, 'com.oi.entidades.CidadeRegiao', 1);


-- Section Permission
INSERT INTO BLC_ADMIN_SEC_PERM_XREF (ADMIN_SECTION_ID, ADMIN_PERMISSION_ID) VALUES (1,1);
INSERT INTO BLC_ADMIN_SEC_PERM_XREF (ADMIN_SECTION_ID, ADMIN_PERMISSION_ID) VALUES (2,1);

-- admin permission xref
INSERT INTO BLC_ADMIN_USER_PERMISSION_XREF (ADMIN_USER_ID, ADMIN_PERMISSION_ID) VALUES (-1, 1);