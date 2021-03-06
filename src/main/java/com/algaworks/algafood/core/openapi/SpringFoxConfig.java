package com.algaworks.algafood.core.openapi;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.apache.tomcat.jni.File;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.EstadoModel;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.PermissaoModel;
import com.algaworks.algafood.api.model.ProdutoModel;
import com.algaworks.algafood.api.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.api.openapi.model.CidadesModelOpenApi;
import com.algaworks.algafood.api.openapi.model.CozinhasModelOpenApi;
import com.algaworks.algafood.api.openapi.model.EstadosModelOpenApi;
import com.algaworks.algafood.api.openapi.model.FormasPagamentoModelOpenApi;
import com.algaworks.algafood.api.openapi.model.GruposModelOpenApi;
import com.algaworks.algafood.api.openapi.model.LinksModelOpenApi;
import com.algaworks.algafood.api.openapi.model.PageableModelOpenApi;
import com.algaworks.algafood.api.openapi.model.PedidosResumoModelOpenApi;
import com.algaworks.algafood.api.openapi.model.PermissoesModelOpenApi;
import com.algaworks.algafood.api.openapi.model.ProdutosModelOpenApi;
import com.algaworks.algafood.api.openapi.model.RestaurantesBasicoModelOpenApi;
import com.algaworks.algafood.api.openapi.model.UsuariosModelOpenApi;
import com.amazonaws.auth.policy.Resource;
import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@Import({ springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class})
public class SpringFoxConfig {
	 	
		@Bean
	    public Docket apiDocket() {
			
			TypeResolver typeResolver = new TypeResolver();  
			
			/*
			 * List<RequestParameter> listaDeParametrosGlobais = new
			 * ArrayList<RequestParameter>(); RequestParameter parametroGLobal = new
			 * RequestParameterBuilder() .name("campos")
			 * .description("Nomes das propriedades para filtrar na resposta, separados por vírgula"
			 * ) .in(ParameterType.QUERY) .required(true) .query(q -> q.model(m ->
			 * m.scalarModel(ScalarType.STRING))) .build();
			 * listaDeParametrosGlobais.add(parametroGLobal);
			 */	        
	        
			return new Docket(DocumentationType.OAS_30)
	                .select()
	                    //.apis(RequestHandlerSelectors.any())
	                	.apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
	                	//.paths(PathSelectors.ant("/restaurantes/*").or(PathSelectors.ant("/cozinhas/*")))
	                	.paths(PathSelectors.any())
	                    .build()
	                .useDefaultResponseMessages(false)
	                .globalResponses(HttpMethod.GET, globalGetResponseMessages())
	                .globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
	                .globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
	                .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
	                
//	                .globalRequestParameters(Collections.singletonList(
//	                				new RequestParameterBuilder()
//			    	                	.name("campos")
//			    	                	.description("Nomes das propriedades para filtrar na resposta, separados por vírgula")
//			    	                	.in(ParameterType.QUERY)
//			    	                	.required(true)
//			    	                	.query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
//			    	                	.build())
//	                		)
	               
	                
	                
	                .additionalModels(typeResolver.resolve(Problem.class))
	                .ignoredParameterTypes(ServletWebRequest.class,
	                        URL.class, URI.class, URLStreamHandler.class, Resource.class,
	                        File.class, InputStream.class)
	                
	                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
	                .directModelSubstitute(Links.class, LinksModelOpenApi.class)
	                
	                .alternateTypeRules(AlternateTypeRules.newRule(
	                		typeResolver.resolve(PagedModel.class, CozinhaModel.class), CozinhasModelOpenApi.class))
	                
	                .alternateTypeRules(AlternateTypeRules.newRule(
	                        typeResolver.resolve(PagedModel.class, PedidoResumoModel.class),
	                        PedidosResumoModelOpenApi.class))
	                
	                .alternateTypeRules(AlternateTypeRules.newRule(
	                        typeResolver.resolve(CollectionModel.class, CidadeModel.class),
	                        CidadesModelOpenApi.class))

	                .alternateTypeRules(AlternateTypeRules.newRule(
	                        typeResolver.resolve(CollectionModel.class, EstadoModel.class),
	                        EstadosModelOpenApi.class))
	                
	                .alternateTypeRules(AlternateTypeRules.newRule(
	                        typeResolver.resolve(CollectionModel.class, FormaPagamentoModel.class),
	                        FormasPagamentoModelOpenApi.class))
	                
	                .alternateTypeRules(AlternateTypeRules.newRule(
	                        typeResolver.resolve(CollectionModel.class, GrupoModel.class),
	                        GruposModelOpenApi.class))
	                
	                .alternateTypeRules(AlternateTypeRules.newRule(
	                        typeResolver.resolve(CollectionModel.class, PermissaoModel.class),
	                        PermissoesModelOpenApi.class))
	                	                
	                .alternateTypeRules(AlternateTypeRules.newRule(
	                	    typeResolver.resolve(CollectionModel.class, ProdutoModel.class),
	                	    ProdutosModelOpenApi.class))
	                
	                .alternateTypeRules(AlternateTypeRules.newRule(
	                	    typeResolver.resolve(CollectionModel.class, RestauranteBasicoModel.class),
	                	    RestaurantesBasicoModelOpenApi.class))

	                .alternateTypeRules(AlternateTypeRules.newRule(
	                        typeResolver.resolve(CollectionModel.class, UsuarioModel.class),
	                        UsuariosModelOpenApi.class))

	                
	                
	                .apiInfo(apiInfo())
	                .tags(	new Tag("Cidades","Gerencia as cidades"),
	                		new Tag("Grupos", "Gerencia os grupos de usuários"),
	                		new Tag("Cozinhas", "Gerencia as cozinhas"),
	                		new Tag("Formas de pagamento", "Gerencia as formas de pagamento"),
	                		new Tag("Pedidos", "Gerencia os pedidos"),
	                		new Tag("Restaurantes","Gerencia os Restaurantes"),
	                		new Tag("Estados","Gerencia os estados"),
	                		new Tag("Produtos","Gerencia os produtos de restaurantes"),
	                		new Tag("Usuários", "Gerencia os usuários"),
	                		new Tag("Estatísticas", "Estatísticas da AlgaFood"),
	                		new Tag("Permissões", "Gerencia as permissões"));
	        
	    }
		
		public ApiInfo apiInfo() {
			return new ApiInfoBuilder()
					.title("AlgaFood API")
					.description("API aberta para clientes e restaurantes")
					.version("1")
					.contact(new Contact("AlgaWorks", "https://www.algaworks.com", "contato@algaworks.com"))
					.build();
		}

		
		private List<Response> globalDeleteResponseMessages() {
			return Arrays.asList(
					new ResponseBuilder()
						.code(descricaoDoCodigo(HttpStatus.INTERNAL_SERVER_ERROR))
						.description("Erro interno do Servidor")
						.representation( MediaType.APPLICATION_JSON )
                        .apply(builderModelProblema())

						.build()
					,new ResponseBuilder()
						.code(descricaoDoCodigo(HttpStatus.BAD_REQUEST))
						.description("Requisição inválida (erro do cliente)")
						.representation( MediaType.APPLICATION_JSON )
                        .apply(builderModelProblema())

						.build()	
					);
		}


		private List<Response> globalPostPutResponseMessages() {
			return Arrays.asList(
					new ResponseBuilder()
						.code(descricaoDoCodigo(HttpStatus.INTERNAL_SERVER_ERROR))
						.description("Erro interno do Servidor")
						.representation( MediaType.APPLICATION_JSON )
                        .apply(builderModelProblema())
						.build()
					,new ResponseBuilder()
						.code(descricaoDoCodigo(HttpStatus.NOT_ACCEPTABLE))
						.description("Recurso não possui representação que pode ser aceita pelo consumidor")
						.build()
					,new ResponseBuilder()
						.code(descricaoDoCodigo(HttpStatus.BAD_REQUEST))
						.description("Requisição inválida (erro do cliente)")
						.representation( MediaType.APPLICATION_JSON )
                        .apply(builderModelProblema())

						.build()	
					,new ResponseBuilder()
						.code(descricaoDoCodigo(HttpStatus.UNSUPPORTED_MEDIA_TYPE))
						.description("Requisição recusada porque o corpo está em um formato não suportado")
						.representation( MediaType.APPLICATION_JSON )
                        .apply(builderModelProblema())

						.build()
					);
		}

		private List<Response> globalGetResponseMessages() {
			return Arrays.asList(
					new ResponseBuilder()
						.code(descricaoDoCodigo(HttpStatus.INTERNAL_SERVER_ERROR))
						.description("Erro interno do Servidor")
						.representation( MediaType.APPLICATION_JSON )
                        .apply(builderModelProblema())

						.build()
					,new ResponseBuilder()
						.code(descricaoDoCodigo(HttpStatus.NOT_ACCEPTABLE))
						.description("Recurso não possui representação que pode ser aceita pelo consumidor")
						.build()	
					);
		}
		
		private String descricaoDoCodigo(HttpStatus httpStatus) {
			return String.valueOf(httpStatus.value());
		}
		private Consumer<RepresentationBuilder> builderModelProblema() {
			return r->r.model(m->m.name("Problema")
					.referenceModel(
							ref->ref.key(
									k->k.qualifiedModelName(
											q->q.name("Problema")
											.namespace("com.algaworks.algafood.api.exceptionhandler")
											))));
		}
}