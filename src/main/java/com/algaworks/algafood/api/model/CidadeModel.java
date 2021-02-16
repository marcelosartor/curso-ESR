package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Relation(collectionRelation = "cidades")
@ApiModel(description = "Representa uma cidade" )
@Setter
@Getter
public class CidadeModel extends RepresentationModel<CidadeModel>{

	@ApiModelProperty(value = "Id da cidade",example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Tubarão")
	private String nome;
	
	private EstadoModel estado;
	
}