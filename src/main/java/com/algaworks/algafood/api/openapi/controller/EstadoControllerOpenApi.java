package com.algaworks.algafood.api.openapi.controller;

import javax.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.ServletWebRequest;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.EstadoModel;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.model.input.EstadoInput;
import com.algaworks.algafood.api.openapi.model.EstadosModelOpenApi;
import com.algaworks.algafood.api.openapi.model.FormasPagamentoModelOpenApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {

	@ApiOperation(value = "Lista os estados",response = EstadosModelOpenApi.class)
	@ApiResponses(value={
			@ApiResponse(responseCode="200", 
						description="OK", 
						content = @Content( examples = @ExampleObject(),
								            schema = @Schema(implementation = EstadosModelOpenApi.class)))
	})
	ResponseEntity<CollectionModel<EstadoModel>> listar(ServletWebRequest request);
	
	
    @ApiOperation("Busca um estado por ID")
    @ApiResponses({
		@ApiResponse(responseCode = "400",description = "Id do estado inválido",content = @Content(schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404",description = "Estado não Encontrado",content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	ResponseEntity<EstadoModel> buscar(@ApiParam("Id de um estado") @PathVariable Long estadoId,ServletWebRequest request);

    @ApiOperation("Cadastra um estado")
    @ApiResponses({
		@ApiResponse(responseCode = "201",description = "Estado cadastrado"),
	})
	EstadoModel adicionar(@ApiParam(name = "Corpo",value="Representação de um novo estado") @RequestBody @Valid EstadoInput estadoInput);

    @ApiOperation("Atualiza um estado por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200",description = "Estado atualizado"),
		@ApiResponse(responseCode = "404",description = "Estado não Encontrado",content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	EstadoModel atualizar(@ApiParam("Id de um estado") @PathVariable @Valid Long estadoId, 
			@ApiParam(name = "Corpo",value="Representação de um estado com os novos dados") @RequestBody EstadoInput estadoInput);

    @ApiOperation("Exclui um estado por ID")
    @ApiResponses({
		@ApiResponse(responseCode = "204",description = "Estado excluido"),
		@ApiResponse(responseCode = "404",description = "Estado não encontrado",content = @Content(schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "409",description = "Estado em uso",content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	void remover(@PathVariable Long estadoId);
	
}
