package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;

@Service
public interface VendaQueryService {
	List<VendaDiaria> consultarVendaDiaria(VendaDiariaFilter filter,String timeOffSet);
	
}
