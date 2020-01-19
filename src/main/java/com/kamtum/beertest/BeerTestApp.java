package com.kamtum.beertest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BeerTestApp {

    public static void main(String[] args) {
        SpringApplication.run(BeerTestApp.class, args);
    }

    // Read console input
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
}
