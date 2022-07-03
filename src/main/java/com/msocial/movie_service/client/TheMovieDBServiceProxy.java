package com.msocial.movie_service.client;

import com.msocial.movie_service.model.dto.TheMovieDBResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.msocial.movie_service.constant.Endpoints.THE_MOVIE_DATABASE_API_V3_DISCOVER_MOVIE;

@FeignClient(name = "the-movie-db-client", url = THE_MOVIE_DATABASE_API_V3_DISCOVER_MOVIE)
public interface TheMovieDBServiceProxy {

    @GetMapping()
    TheMovieDBResponse getFilms(@RequestParam("api_key") String apiKey, @RequestParam("page") int page);
}
