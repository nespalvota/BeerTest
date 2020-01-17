package com.kamtum.beertest.persistence;

import com.kamtum.beertest.domain.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {

}

