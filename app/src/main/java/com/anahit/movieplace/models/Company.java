package com.anahit.movieplace.models;

import java.util.Collection;

public class Company {
    private int id;
    private String name;

    private Collection<Movie> movies;

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

    public Collection<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Collection<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return name;
    }
}
