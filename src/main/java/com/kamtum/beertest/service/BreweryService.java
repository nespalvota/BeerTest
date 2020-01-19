package com.kamtum.beertest.service;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.kamtum.beertest.domain.Brewery;
import com.kamtum.beertest.persistence.BreweryRepository;

@Service
public class BreweryService {

    private BreweryRepository breweryPersistence;

    public BreweryService(BreweryRepository breweryPersistence) {
        this.breweryPersistence = breweryPersistence;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Brewery create(Brewery brewery) {
        return breweryPersistence.save(brewery);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Brewery findById(int id) {
        return breweryPersistence.findById(id).orElseThrow(() ->
                new EntityNotFoundException("brewery not found with id:" + id));
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Brewery> findAll() {
        List<Brewery> breweries = new ArrayList<>();
        Iterator<Brewery> iterator = breweryPersistence.findAll().iterator();
        iterator.forEachRemaining(breweries::add);
        return breweries;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Brewery brewery) {
        breweryPersistence.delete(brewery);
    }

}
