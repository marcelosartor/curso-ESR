package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.api.openapi.model.RestauranteBasicoModelOpenApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {

	@ApiOperation(value="Lista restaurantes")//,hidden = false,response = RestauranteBasicoModelOpenApi.class)
	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nome da projeção de pedidos", 
						  name="projecao", 
						  paramType = "query",
						  type = "String",
						  allowableValues = "apenas-nome")
	})
	//@JsonView(RestauranteView.Resumo.class)
	CollectionModel<RestauranteBasicoModel> listar();  
	
	@ApiIgnore
	@ApiOperation(value="Lista Restaurantes",hidden = true)
	//@JsonView(RestauranteView.ApenasNome.class)		
	CollectionModel<RestauranteApenasNomeModel> listarApenasNomes();
	
	@ApiOperation("Buscar um restaurante por Id")	
	@ApiResponses({
		@ApiResponse(responseCode = "400",description = "Id do Restaurante inválido",content = @Content(schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404",description = "Restaurante não Encontrado",content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	RestauranteModel buscar(@ApiParam("Id de um restaurante") @PathVariable Long restauranteId);
	
	@ApiOperation("Adiciona um restaurante")
	@ApiResponses({
		@ApiResponse(responseCode = "201",description = "Restaurante adicionado"),
	})
	RestauranteModel adicionar(@ApiParam(name = "Corpo",value="Representação de um novo restaurante") @RequestBody @Valid RestauranteInput restauranteInput);
	
	@ApiOperation("Atualiza uma restaurante por Id")
	@ApiResponses({
		@ApiResponse(responseCode = "200",description = "Restaurante atualizado"),
		@ApiResponse(responseCode = "404",description = "Restaurante não Encontrado",content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	RestauranteModel atualizar(@ApiParam("Id de um restaurante") @PathVariable @Valid Long restauranteId,  
			@ApiParam(name = "Corpo",value="Representação de um restaurante com os novos dados") @RequestBody @Valid RestauranteInput restauranteInput);
	
	
	@ApiOperation("Excluir um restaurante por Id")
	@ApiResponses({
		@ApiResponse(responseCode = "204",description = "Restaurante excluido"),
		@ApiResponse(responseCode = "404",description = "Restaurante não encontrado",content = @Content(schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "409",description = "Restaurante em uso",content = @Content(schema = @Schema(implementation = Problem.class)))
	})
	ResponseEntity<RestauranteModel> excluir(@ApiParam("Id de um restaurante") @PathVariable Long restauranteId);
	
	
	@ApiOperation("Ativa um restaurante por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Restaurante ativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))
    })
	ResponseEntity<Void> ativar(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId);
    
    @ApiOperation("Inativa um restaurante por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Restaurante inativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<Void>  inativar(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId);
    
    @ApiOperation("Ativa múltiplos restaurantes")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso")
    })
    void ativarMultiplos(
            @ApiParam(name = "corpo", value = "IDs de restaurantes", required = true)
            List<Long> restauranteIds);
    
    @ApiOperation("Inativa múltiplos restaurantes")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso")
    })
    void inativarMultiplos(
            @ApiParam(name = "corpo", value = "IDs de restaurantes", required = true)
            List<Long> restauranteIds);

    @ApiOperation("Abre um restaurante por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Restaurante aberto com sucesso"),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<Void>  abrir(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId);
    
    @ApiOperation("Fecha um restaurante por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Restaurante fechado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<Void>  fechar(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId);
}
