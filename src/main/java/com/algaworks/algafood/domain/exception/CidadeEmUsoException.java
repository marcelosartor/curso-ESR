		package com.algaworks.algafood.domain.exception;

public class CidadeEmUsoException  extends EntidadeEmUsoException {

	private static final long serialVersionUID = 1L;
	
	public CidadeEmUsoException(String mensagem) {
		super(mensagem);
	}

	public CidadeEmUsoException(Long cidadeId) {
		this(String.format("Cidade de codigo %d não pode ser removida, pois está em uso.", cidadeId));
	}
}

