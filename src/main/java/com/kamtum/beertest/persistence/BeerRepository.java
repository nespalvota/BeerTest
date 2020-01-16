package com.giacom.databasedemo.persistence;

import com.giacom.databasedemo.domain.Beer;
import org.springframework.data.repository.CrudRepository;

public interface BeerRepository extends CrudRepository<Beer, Long> {

}

