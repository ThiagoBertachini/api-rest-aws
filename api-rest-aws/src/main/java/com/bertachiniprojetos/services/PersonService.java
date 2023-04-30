package com.bertachiniprojetos.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bertachiniprojetos.exceptions.ResourceNotFoundException;
import com.bertachiniprojetos.model.Person;
import com.bertachiniprojetos.repositories.PersonRepository;

@Service
public class PersonService {
	
	private Logger logger = Logger.getLogger(PersonService.class.getName());
	
	@Autowired
	private PersonRepository personRepository;
	
	public Person findById(Long id) {
		logger.info("finding one person");
		
		return personRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("No FINDINGS FOR THIS ID"));
	}

	public List<Person> findAll() {
		return personRepository.findAll();
	}
	
	public Person create(Person person) {
		
		logger.info("Creating person");
		return personRepository.save(person);
	}

	public Person update(Person person, Long id) {
		
		logger.info("Updating person");
		
		var entity = personRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("No FINDINGS FOR THIS ID"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		return personRepository.save(entity);
	}

	public void delete(Long id) {
		
		logger.info("Deleting person");
		
		var entity = personRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("No FINDINGS FOR THIS ID"));
		
		personRepository.delete(entity);
	}


}
