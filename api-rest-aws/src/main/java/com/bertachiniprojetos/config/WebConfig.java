package com.bertachiniprojetos.config;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bertachiniprojetos.serialization.converter.YamlJacksonToHttpMessageConverter;


@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new YamlJacksonToHttpMessageConverter());
	}

	private static final MediaType MEDIA_TYPE_APPLICATION_YAML = 
			MediaType.valueOf("application/x-yaml");
	
	

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		/*
		//VIA QUERY PARAM -v-v-v-
		configurer.favorParameter(true) //aceita parametros
		.parameterName("mediaType")
		.ignoreAcceptHeader(true) // ignora parametros no header
		.useRegisteredExtensionsOnly(false)
		.defaultContentType(MediaType.APPLICATION_JSON)
		.mediaType("json", MediaType.APPLICATION_JSON)
		.mediaType("xml", MediaType.APPLICATION_XML);
		*/
		
		//VIA HEADER PARAM -v-v-v-
		//Em header usar key=Accept e value=application/xml ou json
		configurer.favorParameter(false) //aceita parametros
		.ignoreAcceptHeader(false) // ignora parametros no header
		.useRegisteredExtensionsOnly(false)
		.defaultContentType(MediaType.APPLICATION_JSON)
		.mediaType("json", MediaType.APPLICATION_JSON)
		.mediaType("xml", MediaType.APPLICATION_XML)
		.mediaType("x-yaml", MEDIA_TYPE_APPLICATION_YAML);

	}
	
	
	
	

}
