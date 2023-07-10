package com.bertachiniprojetos.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Objects;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import com.bertachiniprojetos.controllers.PersonController;
import com.bertachiniprojetos.data.Vo.V1.PersonVO;
import com.bertachiniprojetos.data.Vo.V2.PersonVOV2;
import com.bertachiniprojetos.exceptions.RequiredObjectIsNullException;
import com.bertachiniprojetos.exceptions.ResourceNotFoundException;
import com.bertachiniprojetos.mappers.DozerMapper;
import com.bertachiniprojetos.mappers.custom.PersonMapper;
import com.bertachiniprojetos.model.Person;
import com.bertachiniprojetos.repositories.PersonRepository;

import jakarta.transaction.Transactional;

@Service
public class PersonService {
	
	private Logger logger = Logger.getLogger(PersonService.class.getName());
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private PersonMapper personMapper;
	
	@Autowired
	private PagedResourcesAssembler<PersonVO> assembler;
	
	public PersonVO findById(Long id) throws Exception {
		logger.info("finding person");
		
		var personEntity = personRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("NO FINDINGS FOR THIS ID"));
	    
		PersonVO personVO = DozerMapper.parseObject(personEntity, PersonVO.class);
		
		personVO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		
		return personVO;
	}

	public PagedModel<EntityModel<PersonVO>> findAll(Pageable pageable) throws Exception {
		logger.info("Listing person");

		var personPaged = personRepository.findAll(pageable);
		
		var personVoPaged = personPaged.map(personP -> DozerMapper.parseObject(personP, PersonVO.class));
		
		Link link;
		personVoPaged.map(
				personVOp ->
				{
					try {
								return personVOp.add(
										linkTo(methodOn(PersonController.class).findById(personVOp.getKey())).withSelfRel());
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							return personVOp;
						});
						 
							link = linkTo(methodOn(
									PersonController.class).findAll(pageable.getPageNumber(), 
																	pageable.getPageSize(), 
																	"asc")).withSelfRel();
				
		return assembler.toModel(personVoPaged, link);
	}
	
	public PagedModel<EntityModel<PersonVO>> findByName(
			String firstNameRequest, 
			Pageable pageable) throws Exception {
		
		logger.info("Listing person");
		
		var personPaged = personRepository.findPersonsByName(firstNameRequest, pageable);
		
		var personVoPaged = personPaged.map(personP -> DozerMapper.parseObject(personP, PersonVO.class));
		
		Link link;
		personVoPaged.map(
				personVOp ->
				{
					try {
						return personVOp.add(
								linkTo(methodOn(PersonController.class).findById(personVOp.getKey())).withSelfRel());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return personVOp;
				});
		
		link = linkTo(methodOn(
				PersonController.class).findAll(pageable.getPageNumber(), 
						pageable.getPageSize(), 
						"asc")).withSelfRel();
		
		return assembler.toModel(personVoPaged, link);
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

	/* Utilizado para métodos não gerenciado pelo próprio Spring DATA JPA
       (DEVIDO A QUERY CRIADA COM MODIFICAÇÃO)*/ 
	@Transactional 
	public PersonVO disable(Long id) throws Exception {
		logger.info("Disable person");
		
		personRepository.disablePerson(id);
		
		var personEntity = personRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("NO FINDINGS FOR THIS ID"));
	    
		PersonVO personVO = DozerMapper.parseObject(personEntity, PersonVO.class);
		
		personVO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		
		return personVO;
	}
	
	public void delete(Long id) {
		
		logger.info("Deleting person");
		
		var entity = personRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("No FINDINGS FOR THIS ID"));
		
		personRepository.delete(entity);
	}
}
