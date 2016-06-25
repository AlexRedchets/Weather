package com.azvk.weather.model_hourly;

public class City {

    private Integer id;
    private String name;
    private Coord coord;
    private String country;
    private Double population;
    private Sys sysForecast;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getPopulation() {
        return population;
    }

    public void setPopulation(Double population) {
        this.population = population;
    }

    public Sys getSysForecast() {
        return sysForecast;
    }

    public void setSysForecast(Sys sysForecast) {
        this.sysForecast = sysForecast;
    }
}
