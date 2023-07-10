package com.bertachiniprojetos.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

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

import com.bertachiniprojetos.data.Vo.V1.BookVO;
import com.bertachiniprojetos.exceptions.RequiredObjectIsNullException;
import com.bertachiniprojetos.model.Book;
import com.bertachiniprojetos.repositories.BookRepository;
import com.bertachiniprojetos.unitTests.mappers.mocks.MockBook;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServiceTest {
	
	MockBook inputBook;
	
	private Long validId;
	
	private String linkValue;
	
	@InjectMocks
	private BookService service;
	
	@Mock
	private BookRepository repository;

	@BeforeEach
	void setUp() throws Exception {
		validId = 1L;
		linkValue = "links: [</api/book/v1/1>;rel=\"self\"]";
		inputBook = new MockBook();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() throws Exception {
		Book bookEntity = inputBook.mockEntity();
		bookEntity.setId(validId);
		
		when(repository.findById(anyLong())).thenReturn(Optional.of(bookEntity));
		
		BookVO result = service.findById(validId);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getTitle());
		assertNotNull(result.getAuthor());
		assertNotNull(result.getLaunchDate());
		assertNotNull(result.getPrice());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains(linkValue));
		
		assertEquals(bookEntity.getId(), result.getKey());
		assertEquals(bookEntity.getTitle(), result.getTitle());
		assertEquals(bookEntity.getAuthor(), result.getAuthor());
		assertEquals(bookEntity.getLaunchDate(), result.getLaunchDate());
	}

	@Test
	void testCreate() throws Exception {
		Book bookEntity = inputBook.mockEntity();
		Book bookEntityPersist = bookEntity;
		bookEntityPersist.setId(validId);
		
		BookVO vo = inputBook.mockVO(1);
		vo.setKey(1l);
		
		when(repository.save(bookEntity)).thenReturn(bookEntityPersist);
		
		BookVO result = service.create(vo);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getAuthor());
		assertNotNull(result.getTitle());
		assertNotNull(result.getLaunchDate());
		assertNotNull(result.getPrice());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains(linkValue));
		
		assertEquals(bookEntityPersist.getId(), result.getKey());
		assertEquals(bookEntityPersist.getAuthor(), result.getAuthor());
		assertEquals(bookEntityPersist.getTitle(), result.getTitle());
		assertEquals(bookEntityPersist.getLaunchDate(), result.getLaunchDate());
	}

	@Test
	void testUpdate() throws Exception {
		Book bookEntity = inputBook.mockEntity();
		bookEntity.setId(validId);
		Book bookEntityPersist = bookEntity;
		bookEntityPersist.setId(validId);
		
		BookVO vo = inputBook.mockVO(1);
		vo.setKey(1l);
		
		when(repository.findById(validId )).thenReturn(Optional.of(bookEntity));
		when(repository.save(bookEntityPersist)).thenReturn(bookEntityPersist);
		
		BookVO result = service.update(vo, validId);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getAuthor());
		assertNotNull(result.getTitle());
		assertNotNull(result.getPrice());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains(linkValue));
		
		assertEquals(bookEntityPersist.getId(), result.getKey());
		assertEquals(bookEntityPersist.getAuthor(), result.getAuthor());
		assertEquals(bookEntityPersist.getTitle(), result.getTitle());
		assertEquals(bookEntityPersist.getPrice(), result.getPrice());
	}

	@Test
	void testDelete() {
		Book bookEntity = inputBook.mockEntity();
		bookEntity.setId(validId);
		
		when(repository.findById(anyLong())).thenReturn(Optional.of(bookEntity));
		doNothing().when(repository).delete(bookEntity);
		
		service.delete(validId);
		
		Mockito.verify(repository, times(1)).findById(validId);
		Mockito.verify(repository, times(1)).delete(bookEntity);
	}

	@Test
	void createMustThrowRequiredObjectsNullExceptionWhenNullBook() throws Exception {				
		String expectedExceptionMsg = "Persisting object must not be null";
		
		Exception ex = assertThrows(RequiredObjectIsNullException.class, 
				() -> service.create(null));
		
		String actualExceptionMsg = ex.getMessage();
		
		assertEquals(actualExceptionMsg, expectedExceptionMsg);
	}
	
	@Test
	void updateMustThrowRequiredObjectsNullExceptionWhenNullBook() throws Exception {				
		assertThrows(RequiredObjectIsNullException.class, () -> service.update(null, validId));
	}
}
