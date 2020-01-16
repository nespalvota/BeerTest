package com.giacom.databasedemo.domain;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Geocode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private int id;
    @NotNull
    private int brewery_id;
    @NotNull
    private double latitude;
    @NotNull
    private double longitude;

    public Geocode() {
    }

    public Geocode(@NotNull int brewery_id, @NotNull double latitude, @NotNull double longitude) {
        this.brewery_id = brewery_id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getBrewery_id() {
        return brewery_id;
    }
    public void setBrewery_id(int id) {
        this.brewery_id = id;
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
}
