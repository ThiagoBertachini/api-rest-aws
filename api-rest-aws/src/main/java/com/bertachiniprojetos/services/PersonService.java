package com.bertachiniprojetos.services;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.bertachiniprojetos.controllers.PersonController;
import com.bertachiniprojetos.data.Vo.V1.PersonVO;
import com.bertachiniprojetos.data.Vo.V2.PersonVOV2;
import com.bertachiniprojetos.exceptions.RequiredObjectIsNullException;
import com.bertachiniprojetos.exceptions.ResourceNotFoundException;
import com.bertachiniprojetos.mappers.DozerMapper;
import com.bertachiniprojetos.mappers.custom.PersonMapper;
import com.bertachiniprojetos.model.Person;
import com.bertachiniprojetos.repositories.PersonRepository;

@Service
public class PersonService {
	
	private Logger logger = Logger.getLogger(PersonService.class.getName());
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private PersonMapper personMapper;
	
	
	public PersonVO findById(Long id) throws Exception {
		logger.info("finding person");
		
		var personEntity = personRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("NO FINDINGS FOR THIS ID"));
	    
		PersonVO personVO = DozerMapper.parseObject(personEntity, PersonVO.class);
		
		personVO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		
		return personVO;
	}

	public List<PersonVO> findAll() {
		logger.info("Listing person");

		var personVO = DozerMapper.parseListObject(personRepository.findAll(), PersonVO.class);
		
		personVO.stream().forEach(p -> //
				{
					try {
						p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel());
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				
		return personVO;
	}
	
	public PersonVO create(PersonVO personVO) throws Exception {
		
		if(Objects.isNull(personVO)) throw new RequiredObjectIsNullException();
		
		logger.info("Creating person");
		
		Person entity = personRepository.save(
				DozerMapper.parseObject(personVO, Person.class));
		
		personVO = DozerMapper.parseObject(entity, PersonVO.class);
		
		personVO.add(linkTo(methodOn(PersonController.class).findById(personVO.getKey())).withSelfRel());
		
		return personVO;
	}
	
	public PersonVOV2 createV2(PersonVOV2 personVOV2) {
		
		if(Objects.isNull(personVOV2)) throw new RequiredObjectIsNullException();

		logger.info("Creating person V2");
		
		var personEntity = personMapper.parsePersonVOV2ToPersonEntity(personVOV2);
		
		return personMapper.parsePersonToPersonVOV2(personRepository.save(personEntity));
	}

	public PersonVO update(PersonVO personVO, Long id) throws Exception {
		
		if(Objects.isNull(personVO)) throw new RequiredObjectIsNullException();
		
		logger.info("Updating person");
		
		var entity = personRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("No FINDINGS FOR THIS ID"));
		
		entity.setFirstName(personVO.getFirstName());
		entity.setLastName(personVO.getLastName());
		entity.setAddress(personVO.getAddress());
		entity.setGender(personVO.getGender());
		
		personVO = DozerMapper.parseObject(personRepository.save(entity), PersonVO.class);
		
		personVO.add(linkTo(methodOn(PersonController.class).findById(personVO.getKey())).withSelfRel());

		return personVO;
	}

	public void delete(Long id) {
		
		logger.info("Deleting person");
		
		var entity = personRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("No FINDINGS FOR THIS ID"));
		
		personRepository.delete(entity);
	}
}
