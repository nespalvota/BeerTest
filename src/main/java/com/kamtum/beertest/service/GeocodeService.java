package com.kamtum.beertest.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kamtum.beertest.domain.Geocode;
import com.kamtum.beertest.persistence.GeocodeRepository;

@Service
public class GeocodeService {

    private GeocodeRepository geocodePersistence;

    public GeocodeService(GeocodeRepository geocodePersistence) {
        this.geocodePersistence = geocodePersistence;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Geocode create(Geocode geocode) {
        return geocodePersistence.save(geocode);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Geocode findById(int id) {
        return geocodePersistence.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Geocode not found with id:" + id));
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Geocode> findAll() {
        List<Geocode> geocodes = new ArrayList<>();
        Iterator<Geocode> iterator = geocodePersistence.findAll().iterator();
        iterator.forEachRemaining(geocodes::add);
        return geocodes;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Geocode geocode) {
        geocodePersistence.delete(geocode);
    }

}
