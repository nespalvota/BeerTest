package com.kamtum.beertest.persistence;

import org.springframework.data.repository.CrudRepository;

import com.kamtum.beertest.domain.Geocode;

public interface GeocodeRepository extends CrudRepository<Geocode, Integer> {

}

