package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.FotoProdutoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.FotoStorageService.NovaFoto;

@Service
public class CatalogoFotoProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private FotoStorageService fotoStorageService;
	
	@Transactional
	public FotoProduto salvar(FotoProduto foto,InputStream inputStream) {
		
		var restauranteId = foto.getRestauranteId(); 
		var produtoId = foto.getProduto().getId();
		var novoNomeArquivo = fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo());
		String nomeArquivoExistente = null;
		// Excluir Foto se Existir
		Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);
		if(fotoExistente.isPresent()) {
			nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
			produtoRepository.delete(fotoExistente.get());
			produtoRepository.flush();
		}
		
		foto.setNomeArquivo(novoNomeArquivo);
		foto = produtoRepository.save(foto);
		produtoRepository.flush();
		
		NovaFoto novaFoto = NovaFoto.builder()
										.nomeArquivo(novoNomeArquivo)
										.contentType(foto.getContentType())
										.inputStream(inputStream)
										.tamanho(foto.getTamanho())
										.build();
		
		fotoStorageService.substituir(nomeArquivoExistente, novaFoto );
		
		
		return foto;
	}

	public FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId) {
		
		return produtoRepository.findFotoById(restauranteId, produtoId)
				.orElseThrow( ()-> new FotoProdutoNaoEncontradaException(restauranteId, produtoId) );
		
	}
	
	@Transactional
	public void removerFoto(Long restauranteId, Long produtoId) {
		
		Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);
		if(fotoExistente.isPresent()) {
			var nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
			produtoRepository.delete(fotoExistente.get());
			produtoRepository.flush();
			fotoStorageService.remover(nomeArquivoExistente);
		}
	}
	
}
