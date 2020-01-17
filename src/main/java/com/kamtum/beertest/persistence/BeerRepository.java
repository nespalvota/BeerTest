package com.kamtum.beertest.persistence;

import com.kamtum.beertest.domain.Beer;
import org.springframework.data.repository.CrudRepository;

public interface BeerRepository extends CrudRepository<Beer, Integer> {

}

