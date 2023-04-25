package com.bertachiniprojetos.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.bertachiniprojetos.model.Person;

@Service
public class PersonService {
	
	private final AtomicLong counter = new AtomicLong();
	private Logger logger = Logger.getLogger(PersonService.class.getName());
	
	public Person findById(String id) {
		logger.info("finding one person");
		
		Person mockPerson = new Person();
		mockPerson.setId(counter.incrementAndGet());
		mockPerson.setFirstName("Mock Name");
		mockPerson.setLastName("Mock LastName");
		mockPerson.setAddress("Mock Address");
		mockPerson.setGender("Mock Gender");
		
		return mockPerson;
	}

	public List<Person> findAll() {
		List<Person> mockPersonList = new ArrayList<>();
		
		for(int i = 1; i < 8; i++) {
			mockPersonList.add(mockerPerson(i));
		}
		return mockPersonList;
	}
	
	public Person create(Person person) {
		
		logger.info("Creating person");
		return person;
	}

	public Person update(Person person, String id) {
		
		logger.info("Updating person");
		person.setId(Long.parseLong(id));
		return person;
	}

	public Object delete(String id) {
		
		logger.info("Deleting person");
		return null;
	}

	private Person mockerPerson(int i) {
		Person mockPerson = new Person();
		mockPerson.setId(counter.incrementAndGet());
		mockPerson.setFirstName("Mock Name" + i);
		mockPerson.setLastName("Mock LastName" + i);
		mockPerson.setAddress("Mock Address" + i);
		mockPerson.setGender("Mock Gender" + i);
		
		return mockPerson;
	}
}
