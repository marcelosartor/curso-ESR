package com.algaworks.algafood.domain.exception;

public class GrupoEmUsoException extends EntidadeEmUsoException {

	private static final long serialVersionUID = 1L;
	
	public GrupoEmUsoException(String mensagem) {
		super(mensagem);
	}

	public GrupoEmUsoException(Long grupoId) {
		this(String.format("Grupo de codigo %d não pode ser removido, pois está em uso.", grupoId));
	}
}	