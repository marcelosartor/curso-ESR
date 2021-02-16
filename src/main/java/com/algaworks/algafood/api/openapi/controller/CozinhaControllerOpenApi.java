package com.algaworks.algafood.api.openapi.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.input.CozinhaInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

	@ApiOperation("Lista as Cozinhas")
	PagedModel<CozinhaModel> listar(@PageableDefault(size = 2) Pageable pageable);
	
	
	@ApiOperation("Busca uma cozinha por Id")
	@ApiResponses({
		@ApiResponse(responseCode = "400",description = "Id da cozinha inválido",content = @Content(schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404",description = "Cozinha não Encontrada",content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	CozinhaModel buscar(@ApiParam("Id de uma cozinha") @PathVariable Long cozinhaId);

	
	@ApiOperation("Cadastra uma cozinha")
	@ApiResponses({
		@ApiResponse(responseCode = "201",description = "Cozinha cadastrada"),
	})
	CozinhaModel adicionar(@ApiParam(name = "Corpo",value="Representação de uma nova cozinha") @RequestBody @Valid CozinhaInput cozinhaInput);

	
	@ApiOperation("Atualiza uma cozinha por Id")
	@ApiResponses({
		@ApiResponse(responseCode = "200",description = "Cozinha Atualizada"),
		@ApiResponse(responseCode = "404",description = "Cozinha não Encontrada",content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	CozinhaModel atualizar(
			@ApiParam("Id de uma cozinha") @PathVariable @Valid Long cozinhaId, 
			@ApiParam(name = "Corpo",value="Representação de uma cozinha com os novos dados") @RequestBody CozinhaInput cozinhaInput);
	
	
	@ApiOperation("Excluir uma cozinha por Id")
	@ApiResponses({
		@ApiResponse(responseCode = "204",description = "Cozinha Excluida"),
		@ApiResponse(responseCode = "404",description = "Cozinha não Encontrada",content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	void excluir(@ApiParam("Id de uma cozinha") @PathVariable Long cozinhaId);
	
}
