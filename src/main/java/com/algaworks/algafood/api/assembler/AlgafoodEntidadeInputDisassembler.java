package com.algaworks.algafood.api.assembler;

import org.springframework.stereotype.Component;


public interface AlgafoodEntidadeInputDisassembler<DTO, ENTIDADE> {

	ENTIDADE toDomainObject(DTO entidadeInput);
	void copyToDomainObject(DTO entidadeInput, ENTIDADE entidade);

}