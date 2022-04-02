package org.movies.service;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.movies.data.MovieRepository;
import org.movies.model.Genre;
import org.movies.model.Movie;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class MovieServiceTest {

    private MovieService movieService;

    @Before
    public void setUp() throws Exception {
        //Declaramos el repositorio con mockito
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);

        //Insertamos datos en la instancia del repositorio
        Mockito.when(movieRepository.findAll()).thenReturn(
                Arrays.asList(
                        new Movie(1, "Dark Knight", 152, Genre.ACTION),
                        new Movie(2, "Memento", 113, Genre.THRILLER),
                        new Movie(3, "There's Something About Mary", 119, Genre.COMEDY),
                        new Movie(4, "Super 8", 112, Genre.THRILLER),
                        new Movie(5, "Scream", 111, Genre.HORROR),
                        new Movie(6, "Home Alone", 103, Genre.COMEDY),
                        new Movie(7, "Matrix", 136, Genre.ACTION)
                )
        );

        //Obtenemos una instacia del servicio para utilizar los metodos y acceder al respositorio
        movieService = new MovieService(movieRepository);
    }

    @Test
    public void returnMoviesByGenre() {
        //Encontramos todas las peliculas con el genero Comedia
        Collection<Movie> movies = movieService.findMoviesByGenre(Genre.COMEDY);

        //Obtenemos los ids de las peliculas con el genero Comedia
        List<Integer> moviesIds = movies.stream().map(movie -> movie.getId()).collect(Collectors.toList());

        //Comparamos los ids de las peliculas con lo que deberian retornar
        assertThat(moviesIds, CoreMatchers.is(Arrays.asList(3,6)));
    }

    @Test
    public void returnMoviesByLength() {

        Collection<Movie> movies = movieService.findMoviesByLength(120);

        List <Integer> moviesIds = movies.stream().map(movie -> movie.getId()).collect(Collectors.toList());
        assertThat(moviesIds, CoreMatchers.is((Arrays.asList(2, 3, 4, 5, 6))));

    }

}