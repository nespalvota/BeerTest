package com.giacom.databasedemo.persistence;

import com.giacom.databasedemo.domain.Brewery;
import org.springframework.data.repository.CrudRepository;

public interface BreweryRepository extends CrudRepository<Brewery, Integer> {

}

