package com.anahit.movieplace.models;

import java.util.Collection;

public class Actor {
    private int id;
    private String name;
    private String surname;
    private String dateOfBirth;
    private int countryId;

    private  Country country;
    private Collection<MovieActorCast> movieActorCasts;

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Collection<MovieActorCast> getMovieActorCasts() {
        return movieActorCasts;
    }

    public void setMovieActorCasts(Collection<MovieActorCast> movieActorCasts) {
        this.movieActorCasts = movieActorCasts;
    }

    @Override
    public String toString() {
        return  name +" " +surname;
    }
}
