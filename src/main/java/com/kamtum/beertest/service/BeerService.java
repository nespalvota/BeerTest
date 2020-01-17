package com.kamtum.beertest.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.kamtum.beertest.domain.Beer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kamtum.beertest.persistence.BeerRepository;

@Service
public class BeerService {

    private BeerRepository beerPersistence;

    public BeerService(BeerRepository beerPersistence) {
        this.beerPersistence = beerPersistence;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Beer create(Beer beer) {
        return beerPersistence.save(beer);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Beer findById(int id) {
        return beerPersistence.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Beer not found with id:" + id));
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Beer> findByBreweryId(int id) {
        List<Beer> beers = new ArrayList<>();
        for (Beer b : beerPersistence.findAll()) {
            if(b.getBrewery_id() == id)
                beers.add(b);
        }
        return beers;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Beer> findAll() {
        List<Beer> beers = new ArrayList<>();
        Iterator<Beer> iterator = beerPersistence.findAll().iterator();
        iterator.forEachRemaining(beers::add);
        return beers;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Beer beer) {
        beerPersistence.delete(beer);
    }

}
