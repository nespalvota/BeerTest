package com.kamtum.beertest.domain;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private int id;
    @NotNull
    private String name;
    @NotNull
    private double latitude;
    @NotNull
    private double longitude;
    @NotNull
    private double distance;

    public Result() {
    }

    public Result(@NotNull int id, @NotNull String name, @NotNull double latitude, @NotNull double longitude,  @NotNull double distance) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getDistance() { return (int) distance; }
    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        System.out.println(String.format("[%4d] %45s (at %f; %f). Distance: %.1f KM", id, name, latitude, longitude, distance));
        return String.format("[%4d] %30s (at %f; %f). Distance: %.2f KM", id, name, latitude, longitude, distance);
    }
}
