package org.movies.data;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.movies.model.Genre;
import org.movies.model.Movie;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

public class MovieRepositoryJdbcTest {
    private MovieRepositoryJdbc movieRepositoryJdbc;
    private DataSource dataSource;

    @Before
    public void setUp() throws Exception {
        dataSource = new DriverManagerDataSource("jdbc:h2:mem:test;MODE=MYSQL","sa","sa");
        ScriptUtils.executeSqlScript(dataSource.getConnection(),new ClassPathResource("sql-scripts/movies-data.sql"));
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        movieRepositoryJdbc = new MovieRepositoryJdbc(jdbcTemplate);
    }

    @Test
    public void loadAllMovies() throws SQLException {


        Collection<Movie> movies = movieRepositoryJdbc.findAll();

        assertThat(movies, CoreMatchers.is(Arrays.asList(
                new Movie(1, "Dark Knight", 152, Genre.ACTION),
                new Movie(2, "Memento", 113, Genre.THRILLER),
                new Movie(3, "Matrix", 136, Genre.ACTION)
        )));

    }

    @Test
    public void loadMovieById() {
        Movie movie = movieRepositoryJdbc.findById(2);
        assertThat(movie,CoreMatchers.is(new Movie(2, "Memento", 113, Genre.THRILLER)));
    }

    @Test
    public void insertMovie() {
        movieRepositoryJdbc.saveOrUpdate(new Movie("Amor y Prejuicio", 140 , Genre.DRAMA));
        Movie movieFromDb = movieRepositoryJdbc.findById(4);
        assertThat(movieFromDb, CoreMatchers.is(new Movie(4,"Amor y Prejuicio", 140 , Genre.DRAMA)));
    }

    @After
    public void tearDown() throws Exception {

        final Statement s = dataSource.getConnection().createStatement();
        s.execute("drop all objects delete files");

    }
}