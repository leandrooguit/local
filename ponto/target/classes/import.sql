insert into USU_USUARIO (USU_ID, USU_NOME, USU_SENHA, USU_LOGIN, USU_PERFIL) values (1, 'Admin', 'a', 'admin', 'USUARIO');

insert into AUT_AUTORIZACAO values ('ROLE_ADMIN');
insert into AUT_AUTORIZACAO values ('ROLE_USER');

insert into USU_USUARIO_AUTORIZACAO (USU_ID, AUT_AUTORIZACAO) values (1, 'ROLE_ADMIN');
insert into USU_USUARIO_AUTORIZACAO (usu_id, AUT_AUTORIZACAO) values (1, 'ROLE_USER');

--insert into par_parametro (par_id, par_nome, par_valor) values (20, 'dataUltimaExecucaoBancoHoras', '25/09/2016')