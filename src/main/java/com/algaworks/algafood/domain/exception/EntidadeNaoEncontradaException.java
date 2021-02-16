		package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;



//@ResponseStatus(value=HttpStatus.NOT_FOUND)
public abstract class EntidadeNaoEncontradaException  extends NegocioException {

	private static final long serialVersionUID = 1L;
	
	public EntidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
}

/*
//@ResponseStatus(value=HttpStatus.NOT_FOUND, reason = "Entidade não encontrada")
//@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class EntidadeNaoEncontradaException  extends ResponseStatusException {

	private static final long serialVersionUID = 1L;
	
	public EntidadeNaoEncontradaException(HttpStatus status, String mensagem) {
		super(status, mensagem);
	}

	public EntidadeNaoEncontradaException(String mensagem) {
		this(HttpStatus.NOT_FOUND,mensagem);
	}
	
}
*/
