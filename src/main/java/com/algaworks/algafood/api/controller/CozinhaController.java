package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.assembler.CozinhaModelAssembler;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.input.CozinhaInput;
import com.algaworks.algafood.api.openapi.controller.CozinhaControllerOpenApi;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

//@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping(path="/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;
	
	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;
	
	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;
	
	@GetMapping()
	public PagedModel<CozinhaModel> listar(@PageableDefault(size = 2) Pageable pageable){
		Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);
	
		PagedModel<CozinhaModel> cozinhasPagedModel = pagedResourcesAssembler.toModel(cozinhasPage, cozinhaModelAssembler);
		
		return cozinhasPagedModel;
		
	}
	/*
	@GetMapping(produces = {MediaType.APPLICATION_XML_VALUE})
	public CozinhasXmlWrapper listarXml(){
		return new CozinhasXmlWrapper(cozinhaRepository.findAll());
	}*/
		
	//@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/{cozinhaId}")
	//public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId){
	public CozinhaModel buscar(@PathVariable Long cozinhaId){
		return cozinhaModelAssembler.toModel(cadastroCozinha.buscarOuFalhar(cozinhaId));
				
		
		/*
		 * Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinhaId);
		 * if(cozinha.isPresent()) { return ResponseEntity.ok(cozinha.get()); }
		 * 
		 * return ResponseEntity.notFound().build();
		 */		
		// linha full
		//return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
				
		// Linha full
		//return ResponseEntity.status(HttpStatus.OK).body(cozinha);
		
		// Atalho para linha full
		//return ResponseEntity.ok(cozinha);
		
		//Exemplo de redirecionamento com location
		//HttpHeaders headers = new HttpHeaders();
		//headers.add(HttpHeaders.LOCATION, "http://api.algafood.local:8080/cozinhas");
		
		//return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
	}
	
	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping
	public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		return cozinhaModelAssembler.toModel(cadastroCozinha.salvar(cozinhaInputDisassembler.toDomainObject(cozinhaInput)));
	}
	
	@PutMapping("/{cozinhaId}")
	public CozinhaModel atualizar(@PathVariable @Valid Long cozinhaId, @RequestBody CozinhaInput cozinhaInput){
		Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);
		cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
		//BeanUtils.copyProperties(cozinhaInput, cozinhaAtual,"id");
		return cozinhaModelAssembler.toModel(cadastroCozinha.salvar(cozinhaAtual));
	}
	
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long cozinhaId){
		cadastroCozinha.excluir(cozinhaId);
	}
	
//	@DeleteMapping("/{cozinhaId}")
//	public ResponseEntity<Cozinha> excluir(@PathVariable Long cozinhaId){
//		try {
//			cadastroCozinha.excluir(cozinhaId);
//		    return ResponseEntity.noContent().build();
//		} catch (EntidadeEmUsoException e) {
//			return ResponseEntity.status(HttpStatus.CONFLICT).build();
//		} catch (EntidadeNaoEncontradaException e) {
//			return ResponseEntity.notFound().build();
//		}	
//		
//	}
	
	
}
