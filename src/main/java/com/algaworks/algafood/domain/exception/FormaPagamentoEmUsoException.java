package com.algaworks.algafood.domain.exception;

public class FormaPagamentoEmUsoException extends EntidadeEmUsoException {

	private static final long serialVersionUID = 1L;
	
	public FormaPagamentoEmUsoException(String mensagem) {
		super(mensagem);
	}

	public FormaPagamentoEmUsoException(Long formaPagamentoId) {
		this(String.format("Forma de Pagamento de codigo %d não pode ser removida, pois está em uso.", formaPagamentoId));
	}
}
