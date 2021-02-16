/* Criando Tabela de restaurante_usuario_responsavel */
create table if not exists restaurante_usuario_responsavel (
	restaurante_id int8 not null, 
	usuario_id int8 not null,
	primary key (restaurante_id,usuario_id)
);

alter table if exists restaurante_usuario_responsavel 
	add constraint fk_restaurante_usuario_responsavel_restaurante foreign key (restaurante_id) references restaurante;
	
alter table if exists restaurante_usuario_responsavel 
	add constraint fk_restaurante_usuario_responsavel_usuario foreign key (usuario_id) references usuario;
