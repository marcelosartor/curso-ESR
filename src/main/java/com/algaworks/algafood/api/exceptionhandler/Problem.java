package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel("Problema")
@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Problem {

	@ApiModelProperty(example = "400",position = 1)
	private Integer status;
	
	@ApiModelProperty(example = "https://algafood.com.br/erro-negocio",position = 10)
	private String type;
	
	@ApiModelProperty(example = "Violação de regra de negócio",position = 20)
	private String title;
	
	@ApiModelProperty(example = "Não existe um cadastro de estado com codigo 1113",position = 30)
	private String detail;
	
	@ApiModelProperty(example = "Não existe um cadastro de estado com codigo 1113",position = 35)
	private String userMessage;
	
	@ApiModelProperty(example = "2020-12-24T00:32:53",position = 40)
	private LocalDateTime timestamp;
	
	@ApiModelProperty(value="Objetos ou campos que geraram o erro",position = 50)
	private List<Object> objects ;
	 
	@ApiModel("ObjetoProblema")
	@Getter
	@Builder	
	public static class Object {
		
		@ApiModelProperty(example = "Estado")	
		private String name;
		@ApiModelProperty(example = "Estado não encontrado")
		private String userMessage;
	}
}