package com.bertachiniprojetos.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bertachiniprojetos.controllers.BookController;
import com.bertachiniprojetos.data.Vo.V1.BookVO;
import com.bertachiniprojetos.exceptions.RequiredObjectIsNullException;
import com.bertachiniprojetos.exceptions.ResourceNotFoundException;
import com.bertachiniprojetos.mappers.DozerMapper;
import com.bertachiniprojetos.mappers.custom.BookMapper;
import com.bertachiniprojetos.model.Book;
import com.bertachiniprojetos.repositories.BookRepository;

@Service
public class BookService {
	
	private Logger logger = Logger.getLogger(BookService.class.getName());
	
	@Autowired
	private BookRepository bookRepository;
	
	
	public BookVO findById(Long id) throws Exception {
		logger.info("finding book");
		
		var bookEntity = bookRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("NO FINDINGS FOR THIS ID"));
	    
		BookVO bookVO = DozerMapper.parseObject(bookEntity, BookVO.class);
		
		bookVO.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		
		return bookVO;
	}

	public List<BookVO> findAll() {
		logger.info("Listing book");

		var bookVO = DozerMapper.parseListObject(bookRepository.findAll(), BookVO.class);
		
		bookVO.stream().forEach(p -> //
				{
					try {
						p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel());
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				
		return bookVO;
	}
	
	public BookVO create(BookVO bookVO) throws Exception {
		
		if(Objects.isNull(bookVO)) throw new RequiredObjectIsNullException();
		
		logger.info("Creating book");
		
		bookVO = DozerMapper.parseObject(bookRepository.save(
						DozerMapper.parseObject(bookVO, Book.class)), BookVO.class);
		
		bookVO.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel());
		
		return bookVO;
	}

	public BookVO update(BookVO bookVO, Long id) throws Exception {
		
		if(Objects.isNull(bookVO)) throw new RequiredObjectIsNullException();
		
		logger.info("Updating book");
		
		var entity = bookRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("No FINDINGS FOR THIS ID"));
		
		entity.setAuthor(bookVO.getAuthor());
		entity.setTitle(bookVO.getTitle());
		entity.setLaunchDate(bookVO.getLaunchDate());
		entity.setPrice(bookVO.getPrice());
		
		bookVO = DozerMapper.parseObject(bookRepository.save(entity), BookVO.class);
		
		bookVO.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel());

		return bookVO;
	}

	public void delete(Long id) {
		
		logger.info("Deleting book");
		
		var entity = bookRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("No FINDINGS FOR THIS ID"));
		
		bookRepository.delete(entity);
	}
}
