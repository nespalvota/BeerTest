package com.giacom.databasedemo.persistence;

import com.giacom.databasedemo.domain.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {

}

