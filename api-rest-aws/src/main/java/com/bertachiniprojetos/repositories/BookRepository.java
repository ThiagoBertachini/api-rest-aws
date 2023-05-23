package com.bertachiniprojetos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bertachiniprojetos.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

}
