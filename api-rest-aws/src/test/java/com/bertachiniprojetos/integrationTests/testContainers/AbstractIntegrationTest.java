package com.bertachiniprojetos.integrationTests.testContainers;

import java.util.Map;
import java.util.stream.Stream;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;

@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {

	public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext>{

		static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0.33");
		
		static void startContainers() {
			Startables.deepStart(Stream.of(mySQLContainer)).join();
		}
		
		static Map<String, String> createConnectionConfiguration() {
			return Map.of(
					"spring.datasource.url", mySQLContainer.getJdbcUrl(),
					"spring.datasource.username", mySQLContainer.getUsername(),
					"spring.datasource.password", mySQLContainer.getPassword()
					);
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public void initialize(ConfigurableApplicationContext applicationContext) {
			startContainers();
			ConfigurableEnvironment environment = applicationContext.getEnvironment();
			MapPropertySource testContainers = new MapPropertySource("testcontainers", 
					(Map) createConnectionConfiguration());
			environment.getPropertySources().addFirst(testContainers);
		}
	}
}
