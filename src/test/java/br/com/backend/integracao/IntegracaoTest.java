package br.com.backend.integracao;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class IntegracaoTest {
	
	@BeforeClass
	static public void setUp() {
		RestAssured.baseURI="http://192.168.18.132:8001/tasks-backend";
	}

	@Test
	public void testaServicoDePe() {
		
		RestAssured.given()
		.when()
			.get()
		.then()
			.statusCode(200)
			.log();
	}
	
	@Test
	public void testaApiTodo() {
		
		RestAssured.given()
		.when()
			.get("/todo")
		.then()
			.statusCode(200)
			.log();
	}
	
	
	@Test
	public void testaApiSavarTask() {
		
		RestAssured.given()
		.when()
			.body("{\"task\":\"teste api\",\"dueDate\":\"2030-01-01\"}")
			.contentType(ContentType.JSON)
			.post("/todo")	
		.then()
			.statusCode(201)
			.log();
		
	}
	
	
	@Test
	public void naoDeveAdicionarTarefaComDataPassada() {
		
		RestAssured.given()
		.when()
			.body("{\"task\":\"teste api\",\"dueDate\":\"2020-01-01\"}")
			.contentType(ContentType.JSON)
			.post("/todo")	
			
		.then()
		.body("message", CoreMatchers.is("Due date must not be in past"))
			.statusCode(400);
		
	}
	
}


