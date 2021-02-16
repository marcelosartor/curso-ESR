package com.algaworks.algafood.domain.exception;

import java.util.UUID;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public PedidoNaoEncontradoException(UUID codigo) {
        super(String.format("Não existe um pedido com código %s", codigo));
    }   
}        
