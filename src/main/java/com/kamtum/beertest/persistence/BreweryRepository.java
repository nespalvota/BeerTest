package com.kamtum.beertest.persistence;

import com.kamtum.beertest.domain.Brewery;
import org.springframework.data.repository.CrudRepository;

public interface BreweryRepository extends CrudRepository<Brewery, Integer> {

}

