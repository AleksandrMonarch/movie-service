package com.msocial.movie_service.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class TheMovieDBResponse {

    @JsonAlias("results")
    private List<MovieDto> movies;
}
