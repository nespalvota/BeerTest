package com.giacom.databasedemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.annotation.PostConstruct;

import com.giacom.databasedemo.persistence.BeerRepository;
import com.giacom.databasedemo.persistence.BreweryRepository;
import com.giacom.databasedemo.persistence.GeocodeRepository;
import com.giacom.databasedemo.persistence.PersonRepository;
import com.giacom.databasedemo.service.BeerService;
import com.giacom.databasedemo.service.BreweryService;
import com.giacom.databasedemo.service.GeocodeService;
import com.giacom.databasedemo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.giacom.databasedemo.domain.*;
import com.giacom.databasedemo.persistence.*;
import com.giacom.databasedemo.service.*;

@SpringBootApplication
public class DatabaseDemoApplication {

    @Autowired
    private PersonService service;
    @Autowired
    private BeerService beerService;
    @Autowired
    private BreweryService breweryService;
    @Autowired
    private GeocodeService geocodeService;

    @Autowired
    private PersonRepository repository;
    @Autowired
    private BeerRepository beerRepository;
    @Autowired
    private BreweryRepository breweryRepository;
    @Autowired
    private GeocodeRepository geocodeRepository;

    public static void main(String[] args) {
        SpringApplication.run(DatabaseDemoApplication.class, args);
    }

    @PostConstruct
    public void findAnswer() {
       /* System.out.print("Enter latitude: ");
        float lat1 = readInput();
        System.out.print("Enter longitude: ");
        float lon1 = readInput();
        System.out.println("HOME: " + lon1 + ", " + lat1);
        // Kaunas: 54.896870, 23.892429
        Double lat2 = geocodeService.findById(1).getLatitude();
        Double lon2 = geocodeService.findById(1).getLongitude();
        System.out.println("The distance is: " + calculateDistance(lat1, lon1, lat2, lon2).intValue() + " + km.");

      /*  List<Beer> findAllBeers = beerService.findAll();
        for (Beer beer : findAllBeers) {
            System.out.println(beer.getId() + ":" + beer.getName());
        }        */
    }

    public static float readInput() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean correct = false;
        float geoCode = 0;
        String input = null;
        while(!correct) {
            try {
                input = br.readLine();
                geoCode = Float.parseFloat(input);
                correct = true;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                System.out.println("Input was not in correct form.");
            }
        }
        return geoCode;
    }

    // Convert value to Radians
    private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }

    // Distance calculation between two points
    // (based on Haversine Formula)
    private static Double calculateDistance (double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth
        Double latDistance = toRad(lat2-lat1);
        Double lonDistance = toRad(lon2-lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        Double distance = R * c;
        return distance;
    }

}
