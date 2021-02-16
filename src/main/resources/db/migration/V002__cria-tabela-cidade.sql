/* Criando Tabela de Cidade */
CREATE TABLE IF NOT EXISTS cidade (
	id  bigserial not null,	
	nome_cidade varchar(80) not null,
	nome_estado varchar(80) not null,
	primary key (id)
);