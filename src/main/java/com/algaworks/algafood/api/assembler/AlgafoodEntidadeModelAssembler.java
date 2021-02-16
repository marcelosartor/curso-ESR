package com.algaworks.algafood.api.assembler;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;


public interface AlgafoodEntidadeModelAssembler<DTO, ENTIDADE> {

	DTO toModel(ENTIDADE entidade);
	List<DTO> toCollectionModel(Collection<ENTIDADE> listEntidades);

}