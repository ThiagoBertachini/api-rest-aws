package com.bertachiniprojetos.integrationTests.swagger;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.bertachiniprojetos.configs.TestConfigs;
import com.bertachiniprojetos.integrationTests.testContainers.AbstractIntegrationTest;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest extends AbstractIntegrationTest {

	@Test
	public void shouldDisplayWebUIPage() {
		var content = given()
		.basePath("/swagger-ui/index.html").port(TestConfigs.SERVER_PORT)
		.when().get().then().statusCode(200)
		.extract().body().asString();
		
		Assertions.assertTrue(content.contains("Swagger UI"));
	}

}
