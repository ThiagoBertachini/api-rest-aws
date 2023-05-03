package com.bertachiniprojetos.mappers.custom;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.bertachiniprojetos.data.Vo.V2.PersonVOV2;
import com.bertachiniprojetos.model.Person;

@Service
public class PersonMapper {

	public PersonVOV2 parsePersonToPersonVOV2(Person person) {
		PersonVOV2 voV2 = new PersonVOV2();
		voV2.setId(person.getId());
		voV2.setFirstName(person.getFirstName());
		voV2.setLastName(person.getLastName());
		voV2.setGender(person.getGender());
		voV2.setAddress(person.getAddress());
		voV2.setBirthDay(new Date());
		
		return voV2;
	}
	
	public Person parsePersonVOV2ToPersonEntity(PersonVOV2 personVOV2) {
		Person personEntity = new Person();
		personEntity.setId(personVOV2.getId());
		personEntity.setFirstName(personVOV2.getFirstName());
		personEntity.setLastName(personVOV2.getLastName());
		personEntity.setGender(personVOV2.getGender());
		personEntity.setAddress(personVOV2.getAddress());
		//personEntity.setBirthDay(new Date());
		
		return personEntity;
	}
}
