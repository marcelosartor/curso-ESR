package com.algaworks.algafood.api.controller;

import java.time.OffsetDateTime;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.algaworks.algafood.api.assembler.EstadoInputDisassembler;
import com.algaworks.algafood.api.assembler.EstadoModelAssembler;
import com.algaworks.algafood.api.model.EstadoModel;
import com.algaworks.algafood.api.model.input.EstadoInput;
import com.algaworks.algafood.api.openapi.controller.EstadoControllerOpenApi;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;


//@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/estados")
public class EstadoController implements EstadoControllerOpenApi {

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	@Autowired
	private EstadoModelAssembler estadoModelAssembler;
	
	@Autowired
	private EstadoInputDisassembler estadoInputDisassembler;
	
	@GetMapping
	public ResponseEntity<CollectionModel<EstadoModel>> listar(ServletWebRequest request){
		
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		String eTag = "0";
		OffsetDateTime dataUltimaAtualizacao = estadoRepository.getDataUltimaAtualizacao();
		if(dataUltimaAtualizacao != null) {
			eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
		}
			
		if(request.checkNotModified(eTag)) {
			return null;
		}
		
		CollectionModel<EstadoModel> estadosModel = estadoModelAssembler.toCollectionModel(estadoRepository.findAll());
		
		return ResponseEntity
				.ok()
				.cacheControl(
						//CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate()) // permite apenas chache local
						CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())    // permite caches locais e publicos - Essa diretiva é padrao
						//CacheControl.noCache())    // exige validação tipo eTag - como se o cache estivesse em expirado
						//CacheControl.noStore()) // não pode ser cacheado.
				.eTag(eTag)
				.body(estadosModel);
	}
	
	@GetMapping("/{estadoId}")
	public ResponseEntity<EstadoModel> buscar(@PathVariable Long estadoId,ServletWebRequest request) {
		
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		
		Estado estado = cadastroEstado.buscarOuFalhar(estadoId);
		
		String eTag = "0";
		if(estado.getDataAtualizacao() != null) {
			eTag = String.valueOf(estado.getDataAtualizacao().toEpochSecond());
		}
			
		if(request.checkNotModified(eTag)) {
			return null;
		}
		
		EstadoModel estadoModel = estadoModelAssembler.toModel(estado);
		
		return ResponseEntity
				.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.eTag(eTag)
				.body(estadoModel); 
	}
	
	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping
	public EstadoModel adicionar(@RequestBody @Valid EstadoInput estadoInput) {
		return estadoModelAssembler.toModel((cadastroEstado.salvar(estadoInputDisassembler.toDomainObject(estadoInput))));
	}
	
	@PutMapping("/{estadoId}")
	public EstadoModel atualizar(@PathVariable @Valid Long estadoId, @RequestBody EstadoInput estadoInput) {
	    Estado estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);
	    estadoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);
	    return estadoModelAssembler.toModel(cadastroEstado.salvar(estadoAtual));
	}
	
	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long estadoId) {
	    cadastroEstado.excluir(estadoId);	
	}
	
	
}
