package com.msocial.movie_service.repository;

import com.msocial.movie_service.model.db.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, String> {

    boolean existsByTitle (String title);

//    @Query(value = "select * from Movies as m  where m.id not in " +
//            "(select m.id from Movies as m " +
//            "left join Users_Movies as us on us.movie_id = m.id " +
//            "where us.user_id = :userId)", nativeQuery = true)
//    List<Movie> getRecommendedMovies(String userId);

    List<Movie> getMoviesByIdNotIn (Collection<String> movieIds);
}
