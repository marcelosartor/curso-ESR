		package com.algaworks.algafood.domain.exception;

public class RestauranteEmUsoException  extends EntidadeEmUsoException {

	private static final long serialVersionUID = 1L;
	
	public RestauranteEmUsoException(String mensagem) {
		super(mensagem);
	}

	public RestauranteEmUsoException(Long restauranteId) {
		this(String.format("Restaurante de codigo %d não pode ser removido, pois está em uso.", restauranteId));
	}
}

