package com.anahit.movieplace.models;

public class MovieActorCast {
    private int id;
    private int movieId;
    private int actorId;

    private Actor actor;
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

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public MovieActorCast( int movieId, int actorId) {
        this.movieId = movieId;
        this.actorId = actorId;
    }

    public MovieActorCast() {
    }
}
