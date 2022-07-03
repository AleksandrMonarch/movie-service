package com.msocial.movie_service.sevice;

import com.msocial.movie_service.model.db.Movie;
import com.msocial.movie_service.model.db.User;

import java.util.Collection;
import java.util.List;

public interface MovieService {

    void saveMovies(Collection<Movie> movies);

    boolean isMovieExist(Movie movie);

    List<Movie> getMovies(Integer pageNumber);

    void addMovieInFavorite(User user, String movieId);

    void removeMovieFromFavorites(User user, String movieId);

    List<Movie> getRecommendedMovies(Collection<Movie> movies);
}
