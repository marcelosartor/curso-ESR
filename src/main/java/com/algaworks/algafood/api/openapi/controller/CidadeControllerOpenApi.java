package com.algaworks.algafood.api.openapi.controller;

import javax.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

	@ApiOperation("Lista as cidades")
	CollectionModel<CidadeModel> listar();
	
	@ApiOperation("Busca uma cidade por Id")
	@ApiResponses({
		@ApiResponse(responseCode = "400",description = "Id da cidade inválido",content = @Content(schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404",description = "Cidade não Encontrada",content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	// Mostra como mudar o nome do parametro na documentacao.
	//CidadeModel buscar(@ApiParam(name = "IdentificacaoCidade", value="Id de uma cidade")  @PathVariable Long cidadeId) {
	CidadeModel buscar(@ApiParam("Id de uma cidade")  @PathVariable Long cidadeId) ;
	
	@ApiOperation("Cadastra uma cidade")
	@ApiResponses({
		@ApiResponse(responseCode = "201",description = "Cidade cadastrada"),
	})
	CidadeModel adicionar(@ApiParam(name = "Corpo",value="Representação de uma nova cidade")	@RequestBody @Valid CidadeInput cidadeInput) ;
		
	@ApiOperation("Atualiza uma cidade por Id")
	@ApiResponses({
		@ApiResponse(responseCode = "200",description = "Cidade Atualizada"),
		@ApiResponse(responseCode = "404",description = "Cidade não Encontrada",content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	CidadeModel atualizar(@ApiParam("Id de uma cidade") @PathVariable @Valid Long cidadeId,
			@ApiParam(name = "Corpo",value="Representação de uma cidade com os novos dados") @RequestBody CidadeInput cidadeInput) ;
	
	
	@ApiOperation("Exclui uma cidade por Id")
	@ApiResponses({
		@ApiResponse(responseCode = "204",description = "Cidade Excluida"),
		@ApiResponse(responseCode = "404",description = "Cidade não Encontrada",content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	void remover(@ApiParam("Id de uma cidade") @PathVariable Long cidadeId) ;
}
