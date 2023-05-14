package com.bertachiniprojetos.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bertachiniprojetos.data.Vo.V1.PersonVO;
import com.bertachiniprojetos.exceptions.RequiredObjectIsNullException;
import com.bertachiniprojetos.model.Person;
import com.bertachiniprojetos.repositories.PersonRepository;
import com.bertachiniprojetos.unitTests.mappers.mocks.MockPerson;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
	
	MockPerson inputPerson;
	
	private Long validId;
	
	private String linkValue;
	
	@InjectMocks
	private PersonService service;
	
	@Mock
	private PersonRepository repository;

	@BeforeEach
	void setUp() throws Exception {
		validId = 1L;
		linkValue = "links: [</api/person/v1/1>;rel=\"self\"]";
		inputPerson = new MockPerson();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() throws Exception {
		Person personEntity = inputPerson.mockEntity();
		personEntity.setId(validId);
		
		when(repository.findById(anyLong())).thenReturn(Optional.of(personEntity));
		
		PersonVO result = service.findById(validId);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getFirstName());
		assertNotNull(result.getLastName());
		assertNotNull(result.getGender());
		assertNotNull(result.getAddress());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains(linkValue));
		
		assertEquals(personEntity.getId(), result.getKey());
		assertEquals(personEntity.getFirstName(), result.getFirstName());
		assertEquals(personEntity.getLastName(), result.getLastName());
		assertEquals(personEntity.getAddress(), result.getAddress());
	}

	@Test
	void testFindAll() {
		List<Person> personEntityList = inputPerson.mockEntityList();
		
		when(repository.findAll()).thenReturn(personEntityList);
		
		List<PersonVO> result = service.findAll();
		
		assertNotNull(result);
		assertEquals(result.get(0).getKey(), personEntityList.get(0).getId());
		assertEquals(result.get(0).getFirstName(), personEntityList.get(0).getFirstName());
		assertEquals(result.get(0).getLastName(), personEntityList.get(0).getLastName());
		assertEquals(result.get(0).getGender(), personEntityList.get(0).getGender());
		assertEquals(result.get(0).getAddress(), personEntityList.get(0).getAddress());
	}

	@Test
	void testCreate() throws Exception {
		Person personEntity = inputPerson.mockEntity();
		Person personEntityPersist = personEntity;
		personEntityPersist.setId(validId);
		
		PersonVO vo = inputPerson.mockVO(1);
		vo.setKey(1l);
		
		when(repository.save(personEntity)).thenReturn(personEntityPersist);
		
		PersonVO result = service.create(vo);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getFirstName());
		assertNotNull(result.getLastName());
		assertNotNull(result.getGender());
		assertNotNull(result.getAddress());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains(linkValue));
		
		assertEquals(personEntityPersist.getId(), result.getKey());
		assertEquals(personEntityPersist.getFirstName(), result.getFirstName());
		assertEquals(personEntityPersist.getLastName(), result.getLastName());
		assertEquals(personEntityPersist.getAddress(), result.getAddress());
	}

	@Test
	void testUpdate() throws Exception {
		Person personEntity = inputPerson.mockEntity();
		personEntity.setId(validId);
		Person personEntityPersist = personEntity;
		personEntityPersist.setId(validId);
		
		PersonVO vo = inputPerson.mockVO(1);
		vo.setKey(1l);
		
		when(repository.findById(validId)).thenReturn(Optional.of(personEntity));
		when(repository.save(personEntityPersist)).thenReturn(personEntityPersist);
		
		PersonVO result = service.update(vo, validId);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getFirstName());
		assertNotNull(result.getLastName());
		assertNotNull(result.getGender());
		assertNotNull(result.getAddress());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains(linkValue));
		
		assertEquals(personEntityPersist.getId(), result.getKey());
		assertEquals(personEntityPersist.getFirstName(), result.getFirstName());
		assertEquals(personEntityPersist.getLastName(), result.getLastName());
		assertEquals(personEntityPersist.getAddress(), result.getAddress());
	}

	@Test
	void testDelete() {
		Person personEntity = inputPerson.mockEntity();
		personEntity.setId(validId);
		
		when(repository.findById(anyLong())).thenReturn(Optional.of(personEntity));
		doNothing().when(repository).delete(personEntity);
		
		service.delete(validId);
		
		Mockito.verify(repository, times(1)).findById(validId);
		Mockito.verify(repository, times(1)).delete(personEntity);
	}

	@Test
	void createMustThrowRequiredObjectsNullExceptionWhenNullPerson() throws Exception {				
		String expectedExceptionMsg = "Persisting object must not be null";
		
		Exception ex = assertThrows(RequiredObjectIsNullException.class, 
				() -> service.create(null));
		
		String actualExceptionMsg = ex.getMessage();
		
		assertEquals(actualExceptionMsg, expectedExceptionMsg);
	}
	
	@Test
	void updateMustThrowRequiredObjectsNullExceptionWhenNullPerson() throws Exception {				
		assertThrows(RequiredObjectIsNullException.class, () -> service.update(null, validId));
	}
}
