package com.bertachiniprojetos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bertachiniprojetos.data.Vo.V1.BookVO;
import com.bertachiniprojetos.services.BookService;
import com.bertachiniprojetos.utils.MediaType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/book/v1")
@Tag(name = "Book", description = "Endpoints for managing Books")
public class BookController {
	
	@Autowired
	private BookService bookService;

	@Operation(
			summary = "Request to return a Book by id", description = "Find by id", tags = {"Book"},
			responses = {
					@ApiResponse(description = "Success", responseCode = "200", 
							content = @Content(schema = @Schema(implementation = BookVO.class))
					),
					@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
			})	
	@GetMapping(value = "/{id}",
	produces = {MediaType.APPLICATION_JSON, 
				MediaType.APPLICATION_XML,
				MediaType.APPLICATION_YAML})
	public ResponseEntity<BookVO> findById(
			@PathVariable(value = "id") Long id) throws Exception {
		return ResponseEntity.ok(bookService.findById(id));
	}
	
	@Operation(
			summary = "Request to return all Book", description = "Find all Book", tags = {"Book"},
			responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = {
							@Content(
									mediaType = "application/json",
									array = @ArraySchema(schema = @Schema(implementation = BookVO.class)))
					}),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
			})
	@GetMapping(produces = {MediaType.APPLICATION_JSON, 
			MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YAML})
	public ResponseEntity<List<BookVO>> findAll() throws Exception {
		return ResponseEntity.ok(bookService.findAll());
	}
	
	@Operation(
			summary = "Request to insert and return a new Book", description = "Creating a Book", tags = {"Book"},
			responses = {
					@ApiResponse(description = "Success", responseCode = "200", 
							content = @Content(schema = @Schema(implementation = BookVO.class))
					),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
			})	
	@PostMapping(consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
						MediaType.APPLICATION_YAML},
				 produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
						 MediaType.APPLICATION_YAML})
	public ResponseEntity<BookVO> create(@RequestBody BookVO bookVO) throws Exception {
		return ResponseEntity.ok(bookService.create(bookVO));
	}
	
	@Operation(
			summary = "Request to update and return a Book", description = "Updating a Book", tags = {"Book"},
			responses = {
					@ApiResponse(description = "Success", responseCode = "200", 
							content = @Content(schema = @Schema(implementation = BookVO.class))
					),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
			})	
	@PutMapping(value = "/{id}",
			consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
					MediaType.APPLICATION_YAML},
		 	produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
		 			MediaType.APPLICATION_YAML})
	public ResponseEntity<BookVO> update(@RequestBody BookVO bookVO,
										@PathVariable(value = "id") Long id) throws Exception {
		return ResponseEntity.ok(bookService.update(bookVO, id));
	}

	@Operation(
			summary = "Request to delete a Book by id", description = "Deleting a Book", tags = {"Book"},
			responses = {
					@ApiResponse(description = "No content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
			})	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) throws Exception {
		bookService.delete(id);
		return ResponseEntity.noContent().build();
	}

}