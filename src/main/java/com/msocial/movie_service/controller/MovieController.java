package com.msocial.movie_service.controller;

import com.msocial.movie_service.enums.LoadedType;
import com.msocial.movie_service.mapper.MovieMapper;
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
import java.util.stream.Collectors;

import static com.msocial.movie_service.constant.Endpoints.*;

@RestController
@Slf4j
public class MovieController {

    private final static int DEFAULT_PAGE_NUMBER = 0;

    private final static int DEFAULT_PAGE_SIZE = 15;

    private final AuthenticatedUserService authenticatedUserService;

    private final MovieService movieServiceDb;

    private final MovieService movieServiceMemory;

    private final UserService userService;

    private final MovieMapper movieMapper;

    public MovieController(AuthenticatedUserService authenticatedUserService,
                           @Qualifier("movieServiceDb") MovieService movieServiceDb,
                           @Qualifier("movieServiceMemory") MovieService movieServiceMemory,
                           UserService userService,
                           MovieMapper movieMapper) {
        this.authenticatedUserService = authenticatedUserService;
        this.movieServiceDb = movieServiceDb;
        this.userService = userService;
        this.movieServiceMemory = movieServiceMemory;
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
    public DataResponse<List<MovieDto>> getRecommendedMovies(@RequestParam("loaderType") LoadedType loadedType) {
        User user = authenticatedUserService.getAuthenticatedUser();
        user = userService.getUserFetchMovies(user.getId());
        switch (loadedType) {
            case IN_MEMORY: {
                List<MovieDto> movies = movieServiceMemory.getRecommendedMovies(user.getMovies()).stream()
                        .map(movieMapper::movieToDto)
                        .collect(Collectors.toList());
                return new DataResponse<>(true, HttpStatus.OK, movies);
            }
            default: {
                List<MovieDto> movies = movieServiceDb.getRecommendedMovies(user.getMovies()).stream()
                        .map(movieMapper::movieToDto)
                        .collect(Collectors.toList());
                return new DataResponse<>(true, HttpStatus.OK, movies);
            }
        }
    }
}
