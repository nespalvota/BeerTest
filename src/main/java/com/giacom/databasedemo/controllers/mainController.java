package com.giacom.databasedemo.controllers;
import com.giacom.databasedemo.domain.Brewery;
import com.giacom.databasedemo.service.BreweryService;
import com.giacom.databasedemo.service.GeocodeService;
import com.giacom.databasedemo.domain.Geocode;
import com.giacom.databasedemo.domain.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
class GreetingController {
    @Autowired
    private GeocodeService geocodeService;
    @Autowired
    private BreweryService breweryService;
    private ArrayList<Brewery> results = new ArrayList<Brewery>() ;

    @GetMapping("/greeting")
    public String greetingForm(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "greeting";
    }

    @PostMapping("/greeting")
    public String greetingSubmit(Model model, @ModelAttribute Greeting greeting, @ModelAttribute Brewery brewery) {
        if(true) {
       // if (checkInput(greeting)) {
            double lat = 51.742503;
            double lon = 19.432956;
           // double lon = Float.parseFloat(greeting.getLongitude());
           // double lat = Float.parseFloat(greeting.getLatitude());
            int max = 1000;
            Geocode[] selected = new Geocode[max];
            Double[] distances = new Double[max];
            int selectedCount = 0;

            double closestDist = 2000;
            int closest = 0;

            List<Geocode> allGeocodes = geocodeService.findAll();
            for (Geocode code : allGeocodes) {
                //Degrees of latitude are parallel so the distance between each degree
                // remains almost constant but since degrees of longitude are farthest apart
                // at the equator and converge at the poles, their distance varies greatly.
                // Therefore, can base geocodes selection only on latitude.
                // Each degree of latitude is approximately 69 miles (111 kilometers)
                // Thus, a brewery is reachable if it's closer than 1000 km (9 degrees of lat)
                double degreeDif = lat - code.getLatitude();
                if (degreeDif < 9 && degreeDif > -9 & selectedCount < max)
                {
                    selected[selectedCount] = code;
                    distances[selectedCount] = calculateDistance(lat, lon, code.getLatitude(), code.getLongitude());
                    if (distances[selectedCount] < closestDist)
                    {
                        closestDist = distances[selectedCount];
                        closest = selectedCount;
                    }
                    selectedCount++;
                }
            }
            System.out.println("Closest: " + selected[closest].getBrewery_id() + " KM: " + distances[closest]);
            /*
            for (Geocode code : allGeocodes) {

            }*/
            results.add(breweryService.findById(1));
            results.add(breweryService.findById(2));
            results.add(breweryService.findById(3));
            results.add(breweryService.findById(4));
            
            model.addAttribute("results", results);
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