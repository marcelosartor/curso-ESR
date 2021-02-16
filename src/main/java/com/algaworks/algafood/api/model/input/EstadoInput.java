package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EstadoInput {
	
	private long id;
	
	@ApiModelProperty(example = "Santa Catarina", required = true)
	@NotBlank
	private String nome;
	
}