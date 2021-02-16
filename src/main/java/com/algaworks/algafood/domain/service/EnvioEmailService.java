package com.algaworks.algafood.domain.service;

import java.util.Map;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

public interface EnvioEmailService {

	void enviar(Mensagem mensagem);
	
	
	@Getter
	@Builder
	class Mensagem{
		
		@Singular
		private Set<String> destinatarios;
		
		@NonNull
		private String assunto;
		
		@NonNull
		private String corpo;
		
		@Singular("variavel")
		private Map<String,Object> variaveis;
		
		/*
		public Mensagem destinatarios(Set<String> destinatarios) {
			this.destinatarios = destinatarios;
			return this;
		}
		
		public Mensagem assuntos(String assuntos) {
			this.assuntos = assuntos;
			return this;
		}
		
		public Mensagem corpo(String corpo) {
			this.corpo = corpo;
			return this;
		}
		*/
		
	}
}
