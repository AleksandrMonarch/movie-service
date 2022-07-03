package com.msocial.movie_service.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class MovieDto {

    private String id;

    private String title;

    @JsonAlias("poster_path")
    private String posterPath;
}
