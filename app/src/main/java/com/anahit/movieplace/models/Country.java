package com.anahit.movieplace.models;

import java.util.Collection;

public class Country {
    private int id;
    private String name;
    private long population;

    private Collection<Actor> actors;
    private  Collection<Movie> movies;

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return this.name;
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

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public Collection<Actor> getActors() {
        return actors;
    }

    public void setActors(Collection<Actor> actors) {
        this.actors = actors;
    }

    public Collection<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Collection<Movie> movies) {
        this.movies = movies;
    }
}
