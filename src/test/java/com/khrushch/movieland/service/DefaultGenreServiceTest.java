package com.khrushch.movieland.service;

import com.khrushch.movieland.dao.GenreDao;
import com.khrushch.movieland.model.Genre;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class DefaultGenreServiceTest {

    @Test
    public void testGetAll() {
        GenreDao mockGenreDao = mock(GenreDao.class);
        when(mockGenreDao.getAll()).thenReturn(getTestGenres());

        DefaultGenreService genreService = new DefaultGenreService();
        genreService.setGenreDao(mockGenreDao);

        List<Genre> actualGenres = genreService.getAll();

        verify(mockGenreDao, times(1)).getAll();
        assertEquals(getTestGenres(), actualGenres);
    }

    @Test
    public void testGetByMovieId() {
        GenreDao mockGenreDao = mock(GenreDao.class);
        when(mockGenreDao.getByMovieId(1L)).thenReturn(getTestGenres());

        DefaultGenreService genreService = new DefaultGenreService();
        genreService.setGenreDao(mockGenreDao);

        List<Genre> actualGenres = genreService.getByMovieId(1L);

        verify(mockGenreDao, times(1)).getByMovieId(1L);
        assertEquals(getTestGenres(), actualGenres);
    }

    private List<Genre> getTestGenres() {
        return new ArrayList<>(Arrays.asList(
                new Genre(1, "вестерн"),
                new Genre(2, "ужасы")
        ));
    }

}