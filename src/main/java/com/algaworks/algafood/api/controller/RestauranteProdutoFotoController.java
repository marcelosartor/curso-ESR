package com.algaworks.algafood.api.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.api.assembler.FotoProdutoModelAssembler;
import com.algaworks.algafood.api.model.FotoProdutoModel;
import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import com.algaworks.algafood.api.openapi.controller.RestauranteProdutoFotoControllerOpenApi;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping(path="/restaurantes/{restauranteId}/produtos/{produtoId}/foto", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoFotoController implements RestauranteProdutoFotoControllerOpenApi {

	@Autowired
	private CatalogoFotoProdutoService catalogoFotoProdutoService;
	
	@Autowired
	private CadastroProdutoService cadastroProduto;
	
	@Autowired
	private FotoProdutoModelAssembler fotoProdutoModelAssembler;
	
	@Autowired
	private FotoStorageService fotoStorageService;
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE) 
	public FotoProdutoModel  atualizarFoto(@PathVariable Long restauranteId,
			@PathVariable Long  produtoId, 
			@Valid FotoProdutoInput fotoProdutoInput,
			@RequestPart(required = true) MultipartFile arquivo) throws IOException {
		
	  Produto produto = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);
	  //MultipartFile arquivo = fotoProdutoInput.getArquivo();
	  			
	  FotoProduto foto = new FotoProduto();
	  foto.setProduto(produto);
	  foto.setDescricao(fotoProdutoInput.getDescricao());
	  foto.setContentType(arquivo.getContentType());
	  foto.setNomeArquivo(arquivo.getOriginalFilename());
	  foto.setTamanho(arquivo.getSize());
		
	  return fotoProdutoModelAssembler.toModel( catalogoFotoProdutoService.salvar(foto,arquivo.getInputStream()) );	
		
	}
	
	
		
	@GetMapping(produces = MediaType.ALL_VALUE)
	public ResponseEntity<?>  servirFoto(@PathVariable Long restauranteId,@PathVariable Long produtoId,
					@RequestHeader(name="accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException  {
		
		try {
			FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
			
			MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
			List<MediaType> mediaTypeAceitas = MediaType.parseMediaTypes(acceptHeader);
			
			verificarCompatibilidadeMediaType(mediaTypeFoto,mediaTypeAceitas);
			
			var fotoRecuperada =  fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
			
			
			if(fotoRecuperada.temUrl()) {
				
				return ResponseEntity
						.status(HttpStatus.FOUND)
						.header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
						.build();
			}else {
				return ResponseEntity.ok()
						.contentType(mediaTypeFoto)
						.body(new InputStreamResource(fotoRecuperada.getInputStream()));
			}	
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}	
		
	  //return fotoProdutoModelAssembler.toModel( catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId) );	
		
	}
	
	@GetMapping() 
	public FotoProdutoModel  recuperarFoto(@PathVariable Long restauranteId,@PathVariable Long produtoId)  {
	  return fotoProdutoModelAssembler.toModel( catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId) );	
		
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerFoto(@PathVariable Long restauranteId,@PathVariable Long produtoId)  {
		
		catalogoFotoProdutoService.removerFoto(restauranteId, produtoId);
		
		
	}
	
	private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypeAceitas) throws HttpMediaTypeNotAcceptableException {
		
		boolean compativel = mediaTypeAceitas.stream()
								.anyMatch(mediaTypeAceita-> mediaTypeAceita.isCompatibleWith(mediaTypeFoto) );
		if(!compativel) {
			throw new HttpMediaTypeNotAcceptableException(mediaTypeAceitas);
		}
	}

	
	/* Teste de upload
	 * @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE) public void
	 * atualizarFoto(@PathVariable Long restauranteId,@PathVariable Long
	 * produtoId, @Valid FotoProdutoInput fotoProdutoInput) {
	 * 
	 * var nomeArquivo =
	 * UUID.randomUUID().toString()+"_"+fotoProdutoInput.getArquivo().
	 * getOriginalFilename();
	 * 
	 * var arquivoFoto = Path.of("/home/msartor/Área de Trabalho/upload",
	 * nomeArquivo);
	 * 
	 * System.out.println(arquivoFoto);
	 * System.out.println(fotoProdutoInput.getArquivo().getContentType());
	 * System.out.println(fotoProdutoInput.getDescricao());
	 * 
	 * try { fotoProdutoInput.getArquivo().transferTo(arquivoFoto); } catch
	 * (Exception e) { throw new RuntimeException(e); } }
	 */	
}
