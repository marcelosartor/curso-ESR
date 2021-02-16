/* Criando Tabela de Estado */
create table if not exists estado (
	id  bigserial not null,	
	nome varchar(80) not null,
	primary key (id)
);

insert into estado (nome) select distinct nome_estado from cidade;

alter table cidade add column estado_id bigint default 0 not null;

update cidade set estado_id = (select id from estado where estado.nome = cidade.nome_estado);

alter table if exists cidade
   add constraint fk_cidade_estado foreign key (estado_id) references estado;  

alter table cidade drop column nome_estado;

alter table cidade rename nome_cidade TO nome;