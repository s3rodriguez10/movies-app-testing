package org.movies.service;

import org.movies.data.MovieRepository;
import org.movies.model.Genre;
import org.movies.model.Movie;

import java.util.Collection;
import java.util.Locale;
import java.util.stream.Collectors;

public class MovieService {
    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Collection<Movie> findMoviesByGenre(Genre genre) {
        return movieRepository.findAll().stream().filter(movie -> movie.getGenre() == genre).collect(Collectors.toList());
    }

    public Collection<Movie> findMoviesByLength(int duration) {
        return movieRepository.findAll().stream().filter(movie -> movie.getMinutes() <= duration).collect(Collectors.toList());
    }

    public Collection<Movie> findMoviesByName(String name){
        return movieRepository.findAll().stream().filter(movie -> movie.getName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
    }

    public Collection<Movie> findMoviesByTemplate(Movie template){
        return movieRepository.findAll().stream().
                filter(movie -> movie.getGenre() == template.getGenre() && movie.getMinutes() <= template.getMinutes()).
                collect(Collectors.toList());
    }
}
