package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.algaworks.algafood.core.validation.Groups;
import com.algaworks.algafood.core.validation.ValorZeroIncluiDescricao;

import lombok.Data;
import lombok.EqualsAndHashCode;

@ValorZeroIncluiDescricao(valorField = "taxaFrete", descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank //(groups = Groups.CadastroRestaurante.class)
	@Column(nullable = false)
	private String nome;
	
	@NotNull 
	@PositiveOrZero //(groups = Groups.CadastroRestaurante.class)
    //----
	//Anotação didatica para composicao
	//@TaxaFrete
	//---
	//Anotação didatica para implementacao de ConstraintValidator
	//@Multiplo(numero=5)
	@Column(name = "taxa_frete",nullable = false)
	private BigDecimal taxaFrete;
	
	@Valid
	@ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
	@NotNull //(groups = Groups.CadastroRestaurante.class)
	@ManyToOne //(fetch = FetchType.LAZY)
	@JoinColumn(name = "cozinha_id", nullable = false)
	private Cozinha cozinha;
	
	@Embedded
	private Endereco endereco;
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "timestamp(0)")
	private OffsetDateTime dataCadastro;
	
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "timestamp(0)")
	private OffsetDateTime dataAtualizacao;
	
	@ManyToMany
	@JoinTable(	name="restaurante_forma_pagamento", 
				joinColumns = @JoinColumn(name="restaurante_id"),
				inverseJoinColumns = @JoinColumn(name="forma_pagamento_id"))
	private Set<FormaPagamento> formasPagamento = new HashSet<>();
	
	@ManyToMany
	@JoinTable(	name="restaurante_usuario_responsavel", 
				joinColumns = @JoinColumn(name="restaurante_id"),
				inverseJoinColumns = @JoinColumn(name="usuario_id"))
	private Set<Usuario> responsaveis = new HashSet<>();
	
	
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();
	
	private Boolean ativo = Boolean.TRUE;
	private Boolean aberto = Boolean.FALSE;

	public void ativar() {
		this.setAtivo(true);
	}
	public void inativar() {
		this.setAtivo(false);
	}
	
	public void abrir() {
		this.setAberto(true);
	}
	public void fechar() {
		this.setAberto(false);
	}
	
	public boolean adicionarFormaPagamento(FormaPagamento formaPagamento) {
		return this.formasPagamento.add(formaPagamento);
	}
	public boolean removerFormaPagamento(FormaPagamento formaPagamento) {
		return this.formasPagamento.remove(formaPagamento);
	}
	
	public boolean removerResponsavel(Usuario usuario) {
	    return this.responsaveis.remove(usuario);
	}

	public boolean adicionarResponsavel(Usuario usuario) {
	    return this.responsaveis.add(usuario);
	}
	
	public boolean aceitaFormaPagamento(FormaPagamento formaPagamento) {
		return this.formasPagamento.contains(formaPagamento);
	}
	
	public boolean naoAceitaFormaPagamento(FormaPagamento formaPagamento) {
	    return !aceitaFormaPagamento(formaPagamento);
	}
	
	public boolean isAberto() {
	    return this.aberto;
	}

	public boolean isFechado() {
	    return !isAberto();
	}

	public boolean isInativo() {
	    return !isAtivo();
	}

	public boolean isAtivo() {
	    return this.ativo;
	}

	public boolean aberturaPermitida() {
	    return isAtivo() && isFechado();
	}

	public boolean ativacaoPermitida() {
	    return isInativo();
	}
	
	public boolean inativacaoPermitida() {
	    return isAtivo();
	}

	public boolean fechamentoPermitido() {
	    return isAberto();
	}       

}
