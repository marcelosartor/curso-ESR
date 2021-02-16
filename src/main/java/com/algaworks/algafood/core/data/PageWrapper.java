package com.algaworks.algafood.core.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class PageWrapper<T> extends PageImpl<T>{

	private static final long serialVersionUID = 1L;

	private Pageable pageable;
	
	public PageWrapper(Page<T> page, Pageable pegeable) {
		super(page.getContent(), pegeable, page.getTotalElements());
		this.pageable = pegeable;
	}
	
	@Override
	public Pageable getPageable() {
		return this.pageable;
	}
}

