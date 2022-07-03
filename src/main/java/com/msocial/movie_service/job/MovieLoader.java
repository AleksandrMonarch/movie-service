package com.msocial.movie_service.job;

import com.msocial.movie_service.client.TheMovieDBServiceProxy;
import com.msocial.movie_service.mapper.MovieMapper;
import com.msocial.movie_service.model.db.Movie;
import com.msocial.movie_service.model.dto.MovieDto;
import com.msocial.movie_service.model.dto.TheMovieDBResponse;
import com.msocial.movie_service.sevice.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@EnableScheduling
@Slf4j
public class MovieLoader {

    private static final int MAX_PAGE_NUMBER = 5;
    
    private final MovieService movieService;

    private final MovieMapper movieMapper;

    private final TheMovieDBServiceProxy theMovieDBServiceProxy;

    @Value("${the-movie-db.api.v3.key}")
    private String apiKey;

    public MovieLoader(@Qualifier("movieServiceDb") MovieService movieService, MovieMapper movieMapper,
                       TheMovieDBServiceProxy theMovieDBServiceProxy) {
        this.movieService = movieService;
        this.movieMapper = movieMapper;
        this.theMovieDBServiceProxy = theMovieDBServiceProxy;
    }

    @Scheduled(fixedDelay = 3, timeUnit = TimeUnit.HOURS)
    public void loadMovies() {
        log.info("MovieLoader is loading new movies");
        Set<Movie> movies = new LinkedHashSet<>();
        for (int pageNumber = 1; pageNumber <= MAX_PAGE_NUMBER; pageNumber++) {
            TheMovieDBResponse response = theMovieDBServiceProxy.getFilms(apiKey, pageNumber);
            Set<MovieDto> movieDtos = new HashSet<>(response.getMovies());
            movies.addAll(movieDtos.stream()
                    .map((movieMapper::movieDtoToMovie))
                    .filter(movie -> !movieService.isMovieExist(movie))
                    .collect(Collectors.toSet()));
        }
        movieService.saveMovies(movies);
        log.info("MovieLoader has finished loading");
    }
}
