package com.bertachiniprojetos.integrationTests.controller.withjson;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.bertachiniprojetos.configs.TestConfigs;
import com.bertachiniprojetos.integrationTests.testContainers.AbstractIntegrationTest;
import com.bertachiniprojetos.integrationTests.vo.PersonVO;
import com.bertachiniprojetos.integrationTests.vo.v1.security.AccountCredentialsVO;
import com.bertachiniprojetos.integrationTests.vo.v1.security.TokenVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerJsonTest extends AbstractIntegrationTest {
	
	private static RequestSpecification specification;
	private static ObjectMapper mapper;

	private static PersonVO personVO;
	private static Long validID;
	
	@BeforeAll
	public static void setUp() {
		mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		personVO = new PersonVO();
		validID = 1L;
	}
	
	@Test
	@Order(0)
	public void authTest() throws JsonProcessingException {
		AccountCredentialsVO credentialsVO = new AccountCredentialsVO("leandro", "admin123");	
		
		var accessTokenVo = given()
				.basePath("/auth/signin")
				.port(TestConfigs.SERVER_PORT)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(credentialsVO)
				.when().post().then().statusCode(200)
		.extract().body().as(TokenVO.class).getAccessToken();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessTokenVo)
				.setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
	}
	
	@Test
	@Order(1)
	public void createShouldCreateWithSuccess() throws JsonProcessingException {
		mockPerson();
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_VALID)
				.body(personVO)
				.when().post().then().statusCode(200)
		.extract().body().asString();
		
		PersonVO createdPerson = mapper.readValue(content, PersonVO.class);
		personVO = createdPerson;
		
		Assertions.assertNotNull(createdPerson);
		
		Assertions.assertEquals(personVO.getFirstName(), createdPerson.getFirstName());
		Assertions.assertEquals(personVO.getLastName(), createdPerson.getLastName());
		Assertions.assertEquals(personVO.getAddress(), createdPerson.getAddress());
	}
	
	@Test
	@Order(2)
	public void createShouldFailWithInvalidOrigin() throws JsonProcessingException {
		mockPerson();
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_INVALID)
				.body(personVO)
				.when().post().then().statusCode(403)
				.extract().body().asString();
		
		Assertions.assertNotNull(content);
		Assertions.assertEquals("Invalid CORS request",content);
	}
	
	@Test
	@Order(3)
	public void findByIdShouldReturnPerson() throws JsonProcessingException {
		mockPerson();
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_VALID)
				.pathParam("id", validID)
				.when().get("{id}").then().statusCode(200)
				.extract().body().asString();
		
		Assertions.assertNotNull(content);
	}
	
	@Test
	@Order(4)
	public void findByIdShouldFailWithInvalidOrigin() throws JsonProcessingException {
		mockPerson();
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_INVALID)
				.pathParam("id", validID)
				.when().get("{id}").then().statusCode(403)
				.extract().body().asString();
		
		Assertions.assertNotNull(content);
		Assertions.assertEquals("Invalid CORS request",content);
	}

	private void mockPerson() {
		personVO.setFirstName("Mock first name");
		personVO.setLastName("Mock last name");
		personVO.setAddress("Mock Address");
		personVO.setGender("Gender");
	}

}
