package com.kamtum.beertest.controllers;
import com.kamtum.beertest.domain.*;
import com.kamtum.beertest.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

@Controller
class GreetingController {
    @Autowired
    private GeocodeService geocodeService;
    @Autowired
    private BreweryService breweryService;
    @Autowired
    private BeerService beerService;
    private static int distanceTravelled;

    @GetMapping("/greeting")
    public String greetingForm(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "greeting";
    }

    @PostMapping("/greeting")
    public String greetingSubmit(Model model, @ModelAttribute Greeting greeting) {
        if(true) {
            double lat = 51.742503;
            double lon = 19.432956;
            Geocode home = new Geocode(0, lat, lon);

            List<Geocode> allGeocodes = geocodeService.findAll();
            Geocode[] filtered = new Geocode[allGeocodes.size()];
            filtered[0] = home;
            int filteredCount = 1;
            for (Geocode code : allGeocodes) {
                if (calculateDistance(code, home) <= 1000)
                {
                    filtered[filteredCount] = code;
                    filteredCount++;
                }
            }
            ArrayList<Result> result3 = buildRoute(beerService, breweryService, filtered, filteredCount, home, 3);

            ArrayList<Beer> beers = new ArrayList<>();
            for (Result res : result3) {
                int dist = (int) res.getDistance();
                beers.addAll(makeBeerList(beerService, res));
            }
            model.addAttribute("results", result3);
            model.addAttribute("distance", distanceTravelled);
            model.addAttribute("beers", beers);
            return "result";
        }
        else
            return "badInput";
    }

    // Check if longitude and latitude input is valid
    public static boolean checkInput(Greeting input) {
        boolean correct = false;
        try {
                double lon = Float.parseFloat(input.getLongitude());
                double lat = Float.parseFloat(input.getLatitude());
                if (lon > -180 && lon < 180 && lat > -90 && lat < 90)
                    correct = true;
            } catch (NumberFormatException e) {
            System.out.println("Input was not in correct form.");
        }
        return correct;
    }
    // Convert value to Radians
    private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }
    // Calculate distance (km) between two points (based on Haversine Formula)
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
    private static Double calculateDistance (Geocode a, Geocode b) {
        return calculateDistance(a.getLatitude(), a.getLongitude(), b.getLatitude(), b.getLongitude());
    }

    private static Double[][] createDistanceMatrix (Geocode[] geocodes, int geocodesCount) {
        Double[][] matrix = new Double[geocodes.length][geocodes.length];
        for (int i = 0; i < geocodesCount; i++ )
        {
            for (int j = i + 1; j < geocodesCount; j++ )
            {
                matrix[i][j] = calculateDistance(geocodes[i], geocodes[j]);
               // System.out.print(matrix[i][j].intValue() + "  ");
            }
           // System.out.println();
        }
        return matrix;
    }

  /*  private static Double[][] createDistanceMatrix (Dictionary<Geocode, Double> codes) {
        Double[][] matrix = new Double[codes.size()][codes.size()];
        for (int i = 0; i < codes.size(); i++ )
        {
            for (int j = i + 1; j < codes.size(); j++ )
            {
                matrix[i][j] = calculateDistance(codes[i], codes[j]);
                // System.out.print(matrix[i][j].intValue() + "  ");
            }
            // System.out.println();
        }
        return matrix;
    }*/

    // Find closest city (brewery) in the distance matrix
    private static int findClosest (Double[][] distances, int distancesCount, int base, ArrayList<Integer> ignore) {
        double closestDist = 100000;
        int closest = 0;
        for (int j = base+1; j < distancesCount; j++ )
        {
            if(distances[base][j] < closestDist)
            {
                boolean notVisited = true;
                for (int a : ignore) {
                    if (a == j)
                        notVisited = false;
                }
                if (notVisited) {
                    closestDist = distances[base][j];
                    closest = j;
                }
            }
        }
        for (int i = 0; i < base; i++ )
        {
            boolean notVisited = true;
            for (int a : ignore) {
                if (a == i)
                    notVisited = false;
            }
            if (notVisited) {
                if (distances[i][base] < closestDist) {
                    closestDist = distances[i][base];
                    closest = i;
                }
            }
        }
        return closest;
    }

    // Find brewery with most beers in the distance matrix
    // (the closest one if there are a few)
    private static int findMostBeers (Geocode[] geocodes, int geocodesCount, BeerService beerService, int currentCity, ArrayList<Integer> ignore) {
        int mostBeersCount = 0;
        int mostBeers = 1;
        double minDist = 100000;
        for (int i = 0; i < geocodesCount; i++) {
            boolean notVisited = true;
            for (int a : ignore) {
                if (a == i)
                    notVisited = false;
            }
            if (notVisited) {
                int beerCount = beerService.findByBreweryId(geocodes[i].getBrewery_id()).size();
                double dist = calculateDistance(geocodes[currentCity], geocodes[i]);
                if (beerCount == mostBeersCount) {
                    if (dist < minDist) {
                        minDist = dist;
                        mostBeersCount = beerCount;
                        mostBeers = i;
                    }
                } else if (beerCount > mostBeersCount) {
                    minDist = dist;
                    mostBeersCount = beerCount;
                    mostBeers = i;
                }
            }
        }
        System.out.println("Most beers: " + mostBeersCount + " at " + geocodes[mostBeers].getBrewery_id());
        return mostBeers;
    }

    private static int findBestValue(BeerService beerService, Double[][] distances, Geocode[] geocodes, int distancesCount, int base, ArrayList<Integer> ignore) {
        double bestValue = 0;
        int bestValueId = 1;
        double value = 0;

        for (int j = base+1; j < distancesCount; j++ )
        {
            boolean notVisited = true;
            for (int a : ignore) {
                if (a == j)
                    notVisited = false;
            }
            if (notVisited) {
                value = beerService.findByBreweryId(geocodes[j].getBrewery_id()).size() / distances[base][j];
                if (value > bestValue ) {
                    bestValue = value;
                    bestValueId = j;
                }
            }
        }
        for (int i = 0; i < base; i++ )
        {
            boolean notVisited = true;
            for (int a : ignore) {
                if (a == i)
                    notVisited = false;
            }
            if (notVisited) {
                value = beerService.findByBreweryId(geocodes[i].getBrewery_id()).size() / distances[i][base];
                if (value > bestValue ) {
                    bestValue = value;
                    bestValueId = i;
                }
            }
        }
        return bestValueId;
    }

    private static ArrayList<Result> buildRoute (BeerService beerService, BreweryService breweryService, Geocode[] geocodes, int geocodesCount, Geocode home, int algorithm) {
        ArrayList<Result> route = new ArrayList();
        route.add(new Result(0, "HOME", home.getLatitude(), home.getLongitude(), 0));

        Double[][] distanceMatrix = createDistanceMatrix(geocodes, geocodesCount);

        int currentCity = 0;
        int nextCity = 0;
        double fuelLeft = 2000;
        double fuelSpent = 0;
        boolean canReachHome = true;
        double distToHomePrevious = 0;

        ArrayList<Integer> ignore = new ArrayList<>();
        ignore.add(currentCity);

        while (fuelLeft > 0 && canReachHome) {
            switch (algorithm) {
                case 1:
                    nextCity = findClosest(distanceMatrix, geocodesCount, currentCity, ignore);
                    break;
                case 2:
                    nextCity = findMostBeers(geocodes, geocodesCount, beerService, currentCity, ignore);
                    break;
                case 3:
                    nextCity = findBestValue(beerService, distanceMatrix, geocodes, geocodesCount, currentCity, ignore);
                    break;
            }

            double distToHome = calculateDistance(geocodes[nextCity], home);
            double dist = 0;
            if (nextCity > currentCity)
                dist = distanceMatrix[currentCity][nextCity];
            else
                dist = distanceMatrix[nextCity][currentCity];

            // if too far from home, don't visit and go home
            if ((distToHome + dist) < fuelLeft) {
                fuelLeft = fuelLeft - dist;
                currentCity = nextCity;
                Result res = new Result(geocodes[currentCity].getBrewery_id(), breweryService.findById(geocodes[currentCity].getBrewery_id()).getName(),
                        geocodes[currentCity].getLatitude(), geocodes[currentCity].getLongitude(), dist);
                route.add(res);
                ignore.add(currentCity);
                fuelSpent += dist;
                distToHomePrevious = distToHome;
            }
            else {
                route.add(new Result(0, "HOME", home.getLatitude(), home.getLongitude(), distToHomePrevious));
                fuelLeft = fuelLeft - distToHomePrevious;
                fuelSpent += distToHomePrevious;
                canReachHome = false;
            }
        }
        System.out.println("FUEL SPENT: " + (int) fuelSpent);
        System.out.println("FUEL LEFT: " + (int) fuelLeft);
        distanceTravelled = (int) fuelSpent;
        return route;
    }

    private static ArrayList<Beer> makeBeerList (BeerService beerService, Result res) {
        ArrayList<Beer> beers = new ArrayList<>();
        if(res.getId() != 0)    // skip 0 as it is home address
            beers.addAll(beerService.findByBreweryId(res.getId()));
        return beers;
    }

    // filter geocodes that are closer than 1000km to home
    private static Geocode[] filterGeocodes (List<Geocode> allGeocodes, Geocode home) {
        Geocode[] filtered = new Geocode[allGeocodes.size()];
        int filteredCount = 0;
        for (Geocode code : allGeocodes) {
            if (calculateDistance(code, home) <= 1000)
            {
                filtered[filteredCount] = code;
            }
        }
        return filtered;
    }
}