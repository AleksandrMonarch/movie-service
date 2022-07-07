package com.msocial.movie_service.sevice.impl.memory;

import com.msocial.movie_service.enums.LoaderType;
import com.msocial.movie_service.exception.memory.MovieNotFoundInMemoryException;
import com.msocial.movie_service.model.db.Movie;
import com.msocial.movie_service.model.db.User;
import com.msocial.movie_service.repository.MovieRepository;
import com.msocial.movie_service.sevice.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service("movieServiceMemory")
public class MovieServiceMemory implements MovieService {

    private static final Map<String, Movie> idMovies = new HashMap<>();

    private final MovieRepository movieRepository;

    public MovieServiceMemory(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @PostConstruct
    private void initMap() {
        updateMap();
    }

    @Override
    public LoaderType getLoaderType() {
        return LoaderType.IN_MEMORY;
    }

    @Override
    public void saveMovies(Collection<Movie> movies) {
        movies.forEach(movie -> idMovies.putIfAbsent(movie.getId(), movie));
    }

    @Override
    public boolean isMovieExist(Movie movie) {
        return idMovies.values().stream()
                .anyMatch(value -> value.getTitle().equals(movie.getTitle()));
    }

    @Override
    public Page<Movie> getMovies(Pageable pageable) {
        List<Movie> movies = new ArrayList<>(idMovies.values());
        int end = (pageable.getPageNumber() + 1) * pageable.getPageSize();
        int start = end - pageable.getPageSize();
        List<Movie> result = new ArrayList<>();
        for (int i = start; i < end; i++) {
            result.add(movies.get(i));
        }
        return new PageImpl<>(result);
    }

    @Override
    public void addMovieInFavorite(User user, String movieId) {
        Movie movie = idMovies.get(movieId);
        if (Objects.isNull(movie)) {
            throw new MovieNotFoundInMemoryException(movieId);
        }
        user.addMovie(movie);
    }

    @Override
    public void removeMovieFromFavorites(User user, String movieId) {
        Movie movie = idMovies.get(movieId);
        if (Objects.isNull(movie)) {
            throw new MovieNotFoundInMemoryException(movieId);
        }
        user.removeMovie(movie);
    }

    @Override
    public List<Movie> getRecommendedMovies(Collection<Movie> movies) {
        if (movies.isEmpty()) {
            return new ArrayList<>(idMovies.values());
        }
        return idMovies.values().stream()
                .filter(movie -> !movies.contains(movie))
                .collect(Collectors.toList());
    }

    public void updateMap() {
        List<Movie> movies = movieRepository.findAll();
        movies.forEach(movie ->  idMovies.putIfAbsent(movie.getId(), movie));
    }
}
