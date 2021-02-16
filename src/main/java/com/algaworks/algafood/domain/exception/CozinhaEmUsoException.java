		package com.algaworks.algafood.domain.exception;

public class CozinhaEmUsoException  extends EntidadeEmUsoException {

	private static final long serialVersionUID = 1L;
	
	public CozinhaEmUsoException(String mensagem) {
		super(mensagem);
	}

	public CozinhaEmUsoException(Long cozinhaId) {
		this(String.format("Cozinha de codigo %d não pode ser removida, pois está em uso.", cozinhaId));
	}
}

