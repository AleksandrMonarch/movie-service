package com.msocial.movie_service.sevice.impl.db;

import com.msocial.movie_service.exception.db.MovieNotFoundException;
import com.msocial.movie_service.model.db.Movie;
import com.msocial.movie_service.model.db.User;
import com.msocial.movie_service.repository.MovieRepository;
import com.msocial.movie_service.sevice.MovieService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.msocial.movie_service.constant.Cache.EXIST_MOVIES;
import static com.msocial.movie_service.constant.Cache.MOVIES_PAGE;

@Service("movieServiceDb")
@EnableCaching
public class MovieServiceDB implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceDB(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    @CacheEvict(cacheNames = {EXIST_MOVIES, MOVIES_PAGE} , allEntries = true)
    public void saveMovies(Collection<Movie> movies) {
        movieRepository.saveAll(movies);
    }

    @Override
    @Cacheable(cacheNames = EXIST_MOVIES, key = "#movie.title")
    public boolean isMovieExist(Movie movie) {
        return movieRepository.existsByTitle(movie.getTitle());
    }

    @Override
    @Cacheable(cacheNames = MOVIES_PAGE, key = "{#pageNumber}")
    public List<Movie> getMovies(Integer pageNumber) {
        PageRequest pageRequest = PageRequest.of(pageNumber, 15);
        Page<Movie> page = movieRepository.findAll(pageRequest);
        return page.getContent();
    }

    @Override
    public void addMovieInFavorite(User user, String movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException(movieId));
        user.addMovie(movie);
        movieRepository.save(movie);
    }

    @Override
    public void removeMovieFromFavorites(User user, String movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException(movieId));
        user.removeMovie(movie);
        movieRepository.save(movie);
    }

    @Override
    public List<Movie> getRecommendedMovies(Collection<Movie> movies) {
        if (movies.isEmpty()) {
            return movieRepository.findAll();
        }
        List<String> movesIds = movies.stream().map(Movie::getId).collect(Collectors.toList());
        return movieRepository.getMoviesByIdNotIn(movesIds);
    }
}
