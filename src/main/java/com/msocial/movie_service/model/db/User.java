package com.msocial.movie_service.model.db;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String email;

    private String username;

    private String name;

    @ManyToMany
    @JoinTable(name = "users_movies",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private List<Movie> movies = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!Objects.equals(email, user.email)) return false;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }

    public void addMovie(Movie movie) {
        if (!movies.contains(movie)) {
            movies.add(movie);
        }
        if (!movie.getUsers().contains(this)) {
            movie.getUsers().add(this);
        }
    }

    public void removeMovie(Movie movie) {
        movies.remove(movie);
        movie.getUsers().remove(this);
    }
}
