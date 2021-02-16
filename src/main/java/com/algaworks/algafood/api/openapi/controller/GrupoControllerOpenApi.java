package com.algaworks.algafood.api.openapi.controller;

import javax.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.model.input.GrupoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

	@ApiOperation("Lista os grupos")
	CollectionModel<GrupoModel> listar();
	
	
	@ApiOperation("Busca um grupo por Id")
	@ApiResponses({
		@ApiResponse(responseCode = "400",description = "Id do grupo inválido",content = @Content(schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404",description = "Grupo não Encontrado",content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	GrupoModel buscar(@ApiParam("Id de um grupo") @PathVariable Long grupoId);
	
	
	@ApiOperation("Cadastra um grupo")
	@ApiResponses({
		@ApiResponse(responseCode = "201",description = "Grupo cadastrado"),
	})
	GrupoModel adicionar(@ApiParam(name = "Corpo",value="Representação de um novo grupo")@RequestBody @Valid GrupoInput grupoInput);
	
	@ApiOperation("Atualiza um grupo por Id")
	@ApiResponses({
		@ApiResponse(responseCode = "200",description = "Grupo Atualizado"),
		@ApiResponse(responseCode = "404",description = "Grupo não Encontrado",content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	GrupoModel atualizar(@ApiParam("Id de um grupo") @PathVariable Long grupoId, 
			@ApiParam(name = "Corpo",value="Representação de um grupo com os novos dados") @RequestBody @Valid GrupoInput grupoInput);
	
	@ApiOperation("Exclui um grupo por Id")
	@ApiResponses({
		@ApiResponse(responseCode = "204",description = "Grupo Excluido"),
		@ApiResponse(responseCode = "404",description = "Grupo não Encontrado",content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	void remover(@ApiParam("Id de um grupo") @PathVariable Long grupoId);
}
