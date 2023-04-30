package com.bertachiniprojetos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bertachiniprojetos.model.Person;
import com.bertachiniprojetos.services.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {
	
	@Autowired
	private PersonService personService;

	@GetMapping(value = "/{id}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Person> findById(
			@PathVariable(value = "id") Long id) throws Exception {
		return ResponseEntity.ok(personService.findById(id));
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Person>> findAll() throws Exception {
		return ResponseEntity.ok(personService.findAll());
	}
	
	@PostMapping
	public ResponseEntity<Person> create(@RequestBody Person person) throws Exception {
		return ResponseEntity.ok(personService.create(person));
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Person> update(@RequestBody Person person,
										@PathVariable(value = "id") Long id) throws Exception {
		return ResponseEntity.ok(personService.update(person, id));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable(value = "id") Long		 id) throws Exception {
		personService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
