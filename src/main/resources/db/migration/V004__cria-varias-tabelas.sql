/* Criando Tabela de forma_pagamento */
create table if not exists forma_pagamento ( 
  id  bigserial not null, 
  descricao varchar(60) not null, 
  
  primary key (id)
 );
  
/* Criando Tabela de grupo */
create table if not exists grupo (
	id  bigserial not null, 
	nome varchar(60) not null, 
	primary key (id)
);

/* Criando Tabela de grupo_permissao */
create table if not exists grupo_permissao (
	grupo_id int8 not null, 
	permissao_id int8 not null,
	primary key (grupo_id,permissao_id)
);

/* Criando Tabela de permissao */
create table if not exists permissao (
	id  bigserial not null, 
	descricao varchar(60) not null, 
	nome varchar(100) not null, 
	primary key (id)
);

/* Criando Tabela de produto */
create table if not exists produto (
	id  bigserial not null, 
	ativo boolean not null, 
	descricao text not null, 
	nome varchar(80) not null, 
	preco numeric(19, 2) not null, 
	restaurante_id int8 not null, 
	primary key (id)
);

/* Criando Tabela de restaurante  */
create table if not exists restaurante (
	id  bigserial not null, 
	nome varchar(80) not null,
	data_atualizacao timestamp(0) not null, 
	data_cadastro timestamp(0) not null,
	
	taxa_frete numeric(10, 2) not null, 
	cozinha_id int8 not null,
	
	endereco_bairro varchar(60), 
	endereco_cep varchar(9), 
	endereco_complemento varchar(60), 
	endereco_logradouro varchar(100), 
	endereco_numero varchar(20), 
	endereco_cidade_id int8,
	 
	primary key (id)
);

/* Criando Tabela de restaurante_forma_pagamento */
create table if not exists restaurante_forma_pagamento (
	restaurante_id int8 not null, 
	forma_pagamento_id int8 not null,
	primary key (restaurante_id,forma_pagamento_id)
);

/* Criando Tabela de usuario */
create table if not exists usuario (
	id  bigserial not null, 
	nome varchar(80) not null,
	email varchar(255) not null, 
	senha varchar(255) not null,
	data_cadastro timestamp(0) not null,
	primary key (id)
);

/* Criando Tabela de usuario_grupo */
create table if not exists usuario_grupo (
	usuario_id int8 not null, 
	grupo_id int8 not null,
	primary key (usuario_id,grupo_id)
);

/* Criando constraints  */
alter table if exists grupo_permissao 
	add constraint fk_grupo_permissao_permissao foreign key (permissao_id) references permissao;
	
alter table if exists grupo_permissao 
	add constraint fk_grupo_permissao_grupo foreign key (grupo_id) references grupo;
	
alter table if exists produto 
	add constraint fk_produto_restaurante foreign key (restaurante_id) references restaurante;
	
alter table if exists restaurante 
	add constraint fk_restaurante_cozinha foreign key (cozinha_id) references cozinha;
	
alter table if exists restaurante 
	add constraint fk_restaurante_cidade foreign key (endereco_cidade_id) references cidade;
	
alter table if exists restaurante_forma_pagamento 
	add constraint fk_restaurante_forma_pagto_forma_pagto foreign key (forma_pagamento_id) references forma_pagamento;
	
alter table if exists restaurante_forma_pagamento 
	add constraint fk_restaurante_forma_pagto_restaurante foreign key (restaurante_id) references restaurante;
	
/*alter table if exists restaurante_forma_pagamento 
    add primary KEY (restaurante_id, forma_pagamento_id);*/	
	
alter table if exists usuario_grupo 
	add constraint fk_usuario_grupo_grupo foreign key (grupo_id) references grupo;
	
alter table if exists usuario_grupo 
	add constraint fk_usuario_grupo_usuario foreign key (usuario_id) references usuario;

