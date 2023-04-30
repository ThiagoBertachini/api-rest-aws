package com.bertachiniprojetos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bertachiniprojetos.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

}
