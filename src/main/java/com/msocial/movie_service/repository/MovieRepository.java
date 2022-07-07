package com.msocial.movie_service.repository;

import com.msocial.movie_service.model.db.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, String> {

    boolean existsByTitle(String title);

    @Query(value = "select * from Movies m where id not in (:movieIds)", nativeQuery = true)
    List<Movie> getMoviesByIdNotIn(Collection<String> movieIds);
}
