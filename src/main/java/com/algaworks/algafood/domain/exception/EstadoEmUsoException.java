		package com.algaworks.algafood.domain.exception;

public class EstadoEmUsoException  extends EntidadeEmUsoException {

	private static final long serialVersionUID = 1L;
	
	public EstadoEmUsoException(String mensagem) {
		super(mensagem);
	}

	public EstadoEmUsoException(Long estadoId) {
		this(String.format("Estado de codigo %d não pode ser removido, pois está em uso.", estadoId));
	}
}

