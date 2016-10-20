insert into usu_usuario (usu_id, usu_nome, usu_senha, usu_login, usu_data_cadastro, usu_versao, usu_local) values (1, 'Admin', 'a', 'admin', '2015-04-04 01:00:00', 0, 'PE');

insert into aut_autorizacao values ('ROLE_ADMIN');
insert into aut_autorizacao values ('ROLE_USER');

insert into usa_usuario_autorizacao (usu_id, aut_autorizacao) values (1, 'ROLE_ADMIN');
insert into usa_usuario_autorizacao (usu_id, aut_autorizacao) values (1, 'ROLE_USER');

insert into par_parametro (par_id, par_versao, par_data_cadastro, par_nome, par_valor) values (20, 0, '2016-04-15 10:45:01', 'dataUltimaExecucaoBancoHoras', '25/09/2016')