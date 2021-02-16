/*
package com.algaworks.algafoodapi;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafoodapi.util.DatabaseCleaner;
import com.algaworks.algafoodapi.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@TestPropertySource("/application-test.properties")
class CadastroCozinhaIT {
	
	private static final int COZINHA_ID_INEXISTENTE = 100;

	private Cozinha cozinhaAmericana;
	private int quantidadeCozinhasCadastradas;
	private String jsonCorretoCozinhaChinesa;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	//@Autowired
	//private Flyway flyway;

	@BeforeEach
	public void setup() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
				
		//flyway.migrate();
		databaseCleaner.clearTables();
		prepararDados();
		
		
		jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource(
				"/json/correto/cozinha-chinesa.json");
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinha() {
		given()
			//.basePath("/cozinhas") // colocado no setup()
			.accept(ContentType.JSON)
			//.port(port) // colocado no setup()
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarQuantidadeCorretaDeCozinhas_QuandoConsultarCozinhas() {
	    given()
	        .accept(ContentType.JSON)
	    .when()
	        .get()
	    .then()
	        .body("", hasSize(quantidadeCozinhasCadastradas));
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinha() {
	    given()
	        .body(jsonCorretoCozinhaChinesa)
	        .contentType(ContentType.JSON)
	        .accept(ContentType.JSON)
	    .when()
	        .post()
	    .then()
	        .statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
	    given()
	        .pathParam("cozinhaId", cozinhaAmericana.getId())
	        .accept(ContentType.JSON)
	    .when()
	        .get("/{cozinhaId}")
	    .then()
	        .statusCode(HttpStatus.OK.value())
	        .body("nome", equalTo(cozinhaAmericana.getNome()));
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
	    given()
	        .pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
	        .accept(ContentType.JSON)
	    .when()
	        .get("/{cozinhaId}")
	    .then()
	        .statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	private void prepararDados() {
		Cozinha cozinhaTailandesa = new Cozinha();
	    cozinhaTailandesa.setNome("Tailandesa");
	    cozinhaRepository.save(cozinhaTailandesa);

	    cozinhaAmericana = new Cozinha();
	    cozinhaAmericana.setNome("Americana");
	    cozinhaRepository.save(cozinhaAmericana);
	    
	    quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();
	}
}





//Exemplo de Teste de Integração

/*
package com.algaworks.algafoodapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.algaworks.algafoodapi.domain.exception.CozinhaEmUsoException;
import com.algaworks.algafoodapi.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.service.CadastroCozinhaService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class CadastroCozinhaIT {

	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Test
	public void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos() {
		
		// cenário
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");
		
		// ação
		novaCozinha = cadastroCozinha.salvar(novaCozinha);
		
		// validação
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}
	
	@Test
	public void deveFalhar_QuandoCadastrarCozinhaSemNome() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);
		
		assertThrows(ConstraintViolationException.class, () -> cadastroCozinha.salvar(novaCozinha));
	}
	
	@Test
	public void deveFalhar_QuandoExcluirCozinhaEmUso() {
		assertThrows(CozinhaEmUsoException.class, () -> cadastroCozinha.excluir(1l));
	}
	
	@Test
	public void deveFalhar_QuandoExcluirCozinhaInexistente() {
		assertThrows(CozinhaNaoEncontradaException.class, () -> cadastroCozinha.excluir(1000l));
	}
	
}
*/