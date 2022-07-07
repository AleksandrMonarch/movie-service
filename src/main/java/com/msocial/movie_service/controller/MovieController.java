package com.msocial.movie_service.controller;

import com.msocial.movie_service.enums.LoaderType;
import com.msocial.movie_service.exception.UnsupportedLoaderType;
import com.msocial.movie_service.mapper.MovieMapper;
import com.msocial.movie_service.model.db.Movie;
import com.msocial.movie_service.model.db.User;
import com.msocial.movie_service.model.dto.BaseResponse;
import com.msocial.movie_service.model.dto.DataResponse;
import com.msocial.movie_service.model.dto.MovieDto;
import com.msocial.movie_service.sevice.MovieService;
import com.msocial.movie_service.sevice.UserService;
import com.msocial.movie_service.sevice.impl.db.AuthenticatedUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.msocial.movie_service.constant.Endpoints.*;

@RestController
@Slf4j
public class MovieController {

    private final static int DEFAULT_PAGE_NUMBER = 0;

    private final static int DEFAULT_PAGE_SIZE = 15;

    private final AuthenticatedUserService authenticatedUserService;

    private final MovieService movieServiceDb;

    private final List<MovieService> movieServices;

    private final UserService userService;

    private final MovieMapper movieMapper;


    public MovieController(AuthenticatedUserService authenticatedUserService,
                           UserService userService,
                           @Qualifier("movieServiceDb") MovieService movieServiceDb,
                           List<MovieService> movieServices,
                           MovieMapper movieMapper) {
        this.authenticatedUserService = authenticatedUserService;
        this.userService = userService;
        this.movieServiceDb = movieServiceDb;
        this.movieServices = movieServices;
        this.movieMapper = movieMapper;
    }

    @GetMapping(API_MOVIES)
    public DataResponse<List<MovieDto>> getMovies(
            @PageableDefault(value = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE) Pageable pageable) {
        List<MovieDto> list = movieServiceDb.getMovies(pageable).getContent().stream()
                .map(movieMapper::movieToDto)
                .collect(Collectors.toList());
        return new DataResponse<>(true, HttpStatus.OK, list);
    }

    @PutMapping(API_FAVORITES_ID)
    public BaseResponse addMovieInFavorites(@PathVariable("id") String movieId) {
        User user = authenticatedUserService.getAuthenticatedUser();
        user = userService.getUserFetchMovies(user.getId());
        movieServiceDb.addMovieInFavorite(user, movieId);
        return new BaseResponse(true, HttpStatus.OK);
    }

    @DeleteMapping(API_FAVORITES_ID)
    public BaseResponse removeMovieFromFavorites(@PathVariable("id") String movieId) {
        User user = authenticatedUserService.getAuthenticatedUser();
        user = userService.getUserFetchMovies(user.getId());
        movieServiceDb.removeMovieFromFavorites(user, movieId);
        return new BaseResponse(true, HttpStatus.OK);
    }

    @GetMapping(API_RECOMMEND)
    public DataResponse<List<MovieDto>> getRecommendedMovies(@RequestParam("loaderType") LoaderType loaderType) {
        User user = authenticatedUserService.getAuthenticatedUser();
        user = userService.getUserFetchMovies(user.getId());

        List<Movie> movies = movieServices.stream()
                .filter(movieService -> Objects.equals(movieService.getLoaderType().getName(), loaderType.getName()))
                .findFirst().orElseThrow(() -> new UnsupportedLoaderType(loaderType))
                .getRecommendedMovies(user.getMovies());

        return new DataResponse<>(true, HttpStatus.OK,
                movies.stream().map(movieMapper::movieToDto).collect(Collectors.toList()));
    }
}
