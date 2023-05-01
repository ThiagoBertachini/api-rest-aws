package com.bertachiniprojetos.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bertachiniprojetos.data.Vo.V1.PersonVO;
import com.bertachiniprojetos.exceptions.ResourceNotFoundException;
import com.bertachiniprojetos.mappers.DozerMapper;
import com.bertachiniprojetos.model.Person;
import com.bertachiniprojetos.repositories.PersonRepository;

@Service
public class PersonService {
	
	private Logger logger = Logger.getLogger(PersonService.class.getName());
	
	@Autowired
	private PersonRepository personRepository;
	
	
	public PersonVO findById(Long id) {
		logger.info("finding person");
		
		var personEntity = personRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("No FINDINGS FOR THIS ID"));
	    
		return DozerMapper.parseObject(personEntity, PersonVO.class);
	}

	public List<PersonVO> findAll() {
		logger.info("Listing person");

		return DozerMapper.parseListObject(personRepository.findAll(), PersonVO.class);
	}
	
	public PersonVO create(PersonVO personVO) {
		
		logger.info("Creating person");
		
		return DozerMapper.parseObject(personRepository.save(
						DozerMapper.parseObject(personVO, Person.class)), PersonVO.class);
	}

	public PersonVO update(PersonVO personVO, Long id) {
		
		logger.info("Updating person");
		
		var entity = personRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("No FINDINGS FOR THIS ID"));
		
		entity.setFirstName(personVO.getFirstName());
		entity.setLastName(personVO.getLastName());
		entity.setAddress(personVO.getAddress());
		entity.setGender(personVO.getGender());
		
		return DozerMapper.parseObject(personRepository.save(entity), PersonVO.class);
}

	public void delete(Long id) {
		
		logger.info("Deleting person");
		
		var entity = personRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("No FINDINGS FOR THIS ID"));
		
		personRepository.delete(entity);
	}


}
