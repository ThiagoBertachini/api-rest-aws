package com.bertachiniprojetos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bertachiniprojetos.data.Vo.V1.PersonVO;
import com.bertachiniprojetos.data.Vo.V2.PersonVOV2;
import com.bertachiniprojetos.services.PersonService;
import com.bertachiniprojetos.utils.MediaType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/person/v1")
@Tag(name = "Person", description = "Endpoints for managing Persons")
public class PersonController {
	
	@Autowired
	private PersonService personService;

	@Operation(
			summary = "Request to return a Person by id", description = "Find by id", tags = {"Person"},
			responses = {
					@ApiResponse(description = "Success", responseCode = "200", 
							content = @Content(schema = @Schema(implementation = PersonVO.class))
					),
					@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
			})	
	@GetMapping(value = "/{id}",
	produces = {MediaType.APPLICATION_JSON, 
				MediaType.APPLICATION_XML,
				MediaType.APPLICATION_YAML})
	public ResponseEntity<PersonVO> findById(
			@PathVariable(value = "id") Long id) throws Exception {
		return ResponseEntity.ok(personService.findById(id));
	}
	
	@Operation(
			summary = "Request to return all Person", description = "Find all Person", tags = {"Person"},
			responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = {
							@Content(
									mediaType = "application/json",
									array = @ArraySchema(schema = @Schema(implementation = PersonVO.class)))
					}),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
			})
	@GetMapping(produces = {MediaType.APPLICATION_JSON, 
			MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YAML})
	public ResponseEntity<List<PersonVO>> findAll() throws Exception {
		return ResponseEntity.ok(personService.findAll());
	}
	
	@Operation(
			summary = "Request to insert and return a new Person", description = "Creating a Person", tags = {"Person"},
			responses = {
					@ApiResponse(description = "Success", responseCode = "200", 
							content = @Content(schema = @Schema(implementation = PersonVO.class))
					),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
			})	
	@PostMapping(consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
						MediaType.APPLICATION_YAML},
				 produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
						 MediaType.APPLICATION_YAML})
	public ResponseEntity<PersonVO> create(@RequestBody PersonVO personVO) throws Exception {
		return ResponseEntity.ok(personService.create(personVO));
	}
	
	@PostMapping(value =  "/v2",
				consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
						MediaType.APPLICATION_YAML},
			 	produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
			 			MediaType.APPLICATION_YAML})
	public ResponseEntity<PersonVOV2> createv2(@RequestBody PersonVOV2 personVOV2) throws Exception {
		return ResponseEntity.ok(personService.createV2(personVOV2));
	}
	
	@Operation(
			summary = "Request to update and return a new Person", description = "Update a Person", tags = {"Person"},
			responses = {
					@ApiResponse(description = "Success", responseCode = "200", 
							content = @Content(schema = @Schema(implementation = PersonVO.class))
					),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
			})
	@PutMapping(value = "/{id}",
			consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
					MediaType.APPLICATION_YAML},
		 	produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
		 			MediaType.APPLICATION_YAML})
	public ResponseEntity<PersonVO> update(@RequestBody PersonVO personVO,
										@PathVariable(value = "id") Long id) throws Exception {
		return ResponseEntity.ok(personService.update(personVO, id));
	}

	@Operation(
			summary = "Request to delete a Person by id", description = "Deleting a Person", tags = {"Person"},
			responses = {
					@ApiResponse(description = "No content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
			})	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) throws Exception {
		personService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
