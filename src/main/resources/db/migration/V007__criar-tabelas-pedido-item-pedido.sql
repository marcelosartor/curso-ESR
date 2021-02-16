/* Criando Tabela de pedido 
 * data_criacao timestamp with time zone not null,
 * */
create table if not exists pedido (
	id  bigserial not null,
	subtotal numeric(10, 2), 
	taxa_frete numeric(10, 2), 
	valor_total numeric(10, 2),
	
	restaurante_id int8 not null,
	usuario_cliente_id int8 not null, 
	forma_pagamento_id int8 not null,
	
	endereco_cidade_id int8,
	endereco_cep varchar(9),
	endereco_logradouro varchar(100),
	endereco_numero varchar(20),
	endereco_complemento varchar(60),
	endereco_bairro varchar(60), 
	 
	status varchar(10), 
	
	data_criacao timestamp(0) ,	 
	data_entrega timestamp(0),
	data_cancelamento timestamp(0), 
	data_confirmacao timestamp(0), 
	 
	primary key (id)
);

/* Criando Tabela de item_pedido */
create table if not exists item_pedido (
	id  bigserial not null, 
	quantidade int4,
	preco_unitario numeric(19, 2),
	preco_total numeric(19, 2), 
		
	observacao varchar(255), 
	 
	pedido_id int8 not null, 
	produto_id int8 not null, 
	
	primary key (id)
);

alter table if exists pedido add constraint fk_pedido_usuario_cliente foreign key (usuario_cliente_id) references usuario;
alter table if exists pedido add constraint fk_pedido_endereco_cidade foreign key (endereco_cidade_id) references cidade;
alter table if exists pedido add constraint fk_pedido_forma_pagamento foreign key (forma_pagamento_id) references forma_pagamento;
alter table if exists pedido add constraint fk_pedido_restaurante foreign key (restaurante_id) references restaurante;

alter table if exists item_pedido add constraint fk_item_pedido_pedido foreign key (pedido_id) references pedido;
alter table if exists item_pedido add constraint fk_item_pedido_produto foreign key (produto_id) references produto;
alter table if exists item_pedido add constraint uk_item_pedido_produto unique (pedido_id, produto_id);     



