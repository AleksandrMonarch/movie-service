package com.msocial.movie_service.controller;

import com.msocial.movie_service.enums.LoadedType;
import com.msocial.movie_service.mapper.MovieMapper;
import com.msocial.movie_service.model.db.Movie;
import com.msocial.movie_service.model.db.User;
import com.msocial.movie_service.model.dto.MovieDto;
import com.msocial.movie_service.sevice.MovieService;
import com.msocial.movie_service.sevice.UserService;
import com.msocial.movie_service.sevice.impl.db.AuthenticatedUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.msocial.movie_service.constant.Endpoints.*;

@RestController
@Slf4j
public class MovieController {

    private final AuthenticatedUserService authenticatedUserService;

    private final MovieService movieServiceDb;

    private final UserService userService;

    private final MovieService movieServiceMemory;

    private final MovieMapper movieMapper;

    public MovieController(AuthenticatedUserService authenticatedUserService,
                           @Qualifier("movieServiceDb") MovieService movieServiceDb,
                           UserService userService, @Qualifier("movieServiceMemory") MovieService movieServiceMemory,
                           MovieMapper movieMapper) {
        this.authenticatedUserService = authenticatedUserService;
        this.movieServiceDb = movieServiceDb;
        this.userService = userService;
        this.movieServiceMemory = movieServiceMemory;
        this.movieMapper = movieMapper;
    }

    @GetMapping(API_MOVIES_PAGE)
    public List<MovieDto> getMovies(@PathVariable("page") Integer pageNumber) {
        List<Movie> movies = movieServiceDb.getMovies(pageNumber);
        return movies.stream().map(movieMapper::movieToDto).collect(Collectors.toList());
    }

    @PutMapping(API_FAVORITES_ID)
    public void addMovieInFavorites(@PathVariable("id") String movieId) {
        User user = authenticatedUserService.getAuthenticatedUser();
        user = userService.getUserFetchMovies(user.getId());
        movieServiceDb.addMovieInFavorite(user, movieId);
    }

    @DeleteMapping(API_FAVORITES_ID)
    public void removeMovieFromFavorites(@PathVariable("id") String movieId) {
        User user = authenticatedUserService.getAuthenticatedUser();
        user = userService.getUserFetchMovies(user.getId());
        movieServiceDb.removeMovieFromFavorites(user, movieId);
    }

    @GetMapping(API_RECOMMEND)
    public List<MovieDto> getRecommendedMovies(@RequestParam("loaderType")LoadedType loadedType) {
        User user = authenticatedUserService.getAuthenticatedUser();
        user = userService.getUserFetchMovies(user.getId());
        switch (loadedType) {
            case IN_MEMORY: {
                List<Movie> movies = movieServiceMemory.getRecommendedMovies(user.getMovies());
                return movies.stream().map(movieMapper::movieToDto).collect(Collectors.toList());
            }
            default: {
                List<Movie> movies = movieServiceDb.getRecommendedMovies(user.getMovies());
                return movies.stream().map(movieMapper::movieToDto).collect(Collectors.toList());
            }
        }
    }
}
