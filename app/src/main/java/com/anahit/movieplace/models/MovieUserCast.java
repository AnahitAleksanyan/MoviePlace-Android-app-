package com.anahit.movieplace.models;

public class MovieUserCast {
    private int id;
    private String userId;
    private int movieId;

    private tbIUser tbIUser;
    private Movie movie;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setUsername(String username) {
        this.userId = username;
    }

    public String getUsername() {
        return userId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public tbIUser getUser() {
        return tbIUser;
    }

    public void setUser(tbIUser user) {
        this.tbIUser = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public MovieUserCast(int movieId, String username) {
        this.userId = username;
        this.movieId = movieId;
    }

    public MovieUserCast() {
    }
}
