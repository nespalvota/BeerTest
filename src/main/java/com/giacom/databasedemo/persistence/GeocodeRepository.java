package com.giacom.databasedemo.persistence;

import org.springframework.data.repository.CrudRepository;

import com.giacom.databasedemo.domain.Geocode;

public interface GeocodeRepository extends CrudRepository<Geocode, Integer> {

}

