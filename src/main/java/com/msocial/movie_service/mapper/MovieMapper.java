package com.msocial.movie_service.mapper;

import com.msocial.movie_service.model.db.Movie;
import com.msocial.movie_service.model.dto.MovieDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    Movie movieDtoToMovie(MovieDto movieDto);

    MovieDto movieToDto(Movie movie);
}
