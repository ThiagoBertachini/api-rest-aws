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

@RestController
@RequestMapping("api/person/v1")
public class PersonController {
	
	@Autowired
	private PersonService personService;

	@GetMapping(value = "/{id}",
			produces = {MediaType.APPLICATION_JSON, 
						MediaType.APPLICATION_XML,
						MediaType.APPLICATION_YAML})
	public ResponseEntity<PersonVO> findById(
			@PathVariable(value = "id") Long id) throws Exception {
		return ResponseEntity.ok(personService.findById(id));
	}
	
	@GetMapping(produces = {MediaType.APPLICATION_JSON, 
							MediaType.APPLICATION_XML,
							MediaType.APPLICATION_YAML})
	public ResponseEntity<List<PersonVO>> findAll() throws Exception {
		return ResponseEntity.ok(personService.findAll());
	}
	
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
	
	@PutMapping(value = "/{id}",
			consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
					MediaType.APPLICATION_YAML},
		 	produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
		 			MediaType.APPLICATION_YAML})
	public ResponseEntity<PersonVO> update(@RequestBody PersonVO personVO,
										@PathVariable(value = "id") Long id) throws Exception {
		return ResponseEntity.ok(personService.update(personVO, id));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) throws Exception {
		personService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
