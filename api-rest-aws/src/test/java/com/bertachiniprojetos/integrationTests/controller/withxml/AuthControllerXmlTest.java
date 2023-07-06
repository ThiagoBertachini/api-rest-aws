package com.bertachiniprojetos.integrationTests.controller.withxml;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.bertachiniprojetos.configs.TestConfigs;
import com.bertachiniprojetos.integrationTests.testContainers.AbstractIntegrationTest;
import com.bertachiniprojetos.integrationTests.vo.v1.security.AccountCredentialsVO;
import com.bertachiniprojetos.integrationTests.vo.v1.security.TokenVO;
import com.fasterxml.jackson.core.JsonProcessingException;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class AuthControllerXmlTest extends AbstractIntegrationTest {
	
	private static TokenVO tokenVO;
	
	@BeforeAll
	public static void setUp() {
	}
	
	@Test
	@Order(1)
	public void testSignIn() throws JsonProcessingException {
		
		AccountCredentialsVO credentialsVO = new AccountCredentialsVO("leandro", "admin123");	
		
		tokenVO = given()
				.basePath("/auth/signin")
				.port(TestConfigs.SERVER_PORT)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.body(credentialsVO)
				.when().post().then().statusCode(200)
		.extract().body().as(TokenVO.class);
		
		assertNotNull(tokenVO.getAccessToken());
		assertNotNull(tokenVO.getRefreshToken());
	}
	
	@Test
	@Order(2)
	public void testRefreshToken() throws JsonProcessingException {
				
		var refreshTokenVO = given()
				.basePath("/auth/refresh")
				.port(TestConfigs.SERVER_PORT)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.pathParam("username", tokenVO.getUsername())
				.header(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenVO.getAccessToken())
				.when().put("{username}").then().statusCode(200)
				.extract().body().as(TokenVO.class);
		
		assertNotNull(refreshTokenVO.getAccessToken());
		assertNotNull(refreshTokenVO.getRefreshToken());
	}
}
