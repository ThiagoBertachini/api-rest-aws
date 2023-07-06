package com.bertachiniprojetos.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Objects;
import java.util.logging.Logger;

import org.hibernate.sql.results.graph.AssemblerCreationState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import com.bertachiniprojetos.controllers.BookController;
import com.bertachiniprojetos.controllers.PersonController;
import com.bertachiniprojetos.data.Vo.V1.BookVO;
import com.bertachiniprojetos.data.Vo.V1.PersonVO;
import com.bertachiniprojetos.exceptions.RequiredObjectIsNullException;
import com.bertachiniprojetos.exceptions.ResourceNotFoundException;
import com.bertachiniprojetos.mappers.DozerMapper;
import com.bertachiniprojetos.model.Book;
import com.bertachiniprojetos.repositories.BookRepository;

@Service
public class BookService {
	
	private Logger logger = Logger.getLogger(BookService.class.getName());
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	private PagedResourcesAssembler<BookVO> assembler;
	
	public BookVO findById(Long id) throws Exception {
		logger.info("finding book");
		
		var bookEntity = bookRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("NO FINDINGS FOR THIS ID"));
	    
		BookVO bookVO = DozerMapper.parseObject(bookEntity, BookVO.class);
		
		bookVO.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		
		return bookVO;
	}

	public PagedModel<EntityModel<BookVO>> findAll(Pageable pageable) throws Exception {
		logger.info("Listing books");

		var bookPaged = bookRepository.findAll(pageable);
		
		var bookVoPaged = bookPaged.map(book -> DozerMapper.parseObject(book, BookVO.class));
		
		Link link;
		bookVoPaged.map(
				bookVoP ->
				{
					try {
								return bookVoP.add(
										linkTo(methodOn(BookController.class).findById(bookVoP.getKey())).withSelfRel());
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							return bookVoP;
						});
						 
							link = linkTo(methodOn(
									BookController.class).findAll(pageable.getPageNumber(), 
																	pageable.getPageSize(), 
																	"asc")).withSelfRel();
				
		return assembler.toModel(bookVoPaged, link);
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
