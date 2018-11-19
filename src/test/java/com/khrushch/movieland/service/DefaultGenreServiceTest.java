package com.khrushch.movieland.service;

import com.khrushch.movieland.dao.GenreDao;
import com.khrushch.movieland.dao.cached.CachedGenreDao;
import com.khrushch.movieland.model.Genre;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class DefaultGenreServiceTest {

    @Test
    public void getAll() {
        GenreDao mockGenreDao = mock(GenreDao.class);
        when(mockGenreDao.getAll()).thenReturn(getTestGenres());

        DefaultGenreService genreService = new DefaultGenreService();
        genreService.setGenreDao(mockGenreDao);

        List<Genre> actualGenres = genreService.getAll();

        verify(mockGenreDao, times(1)).getAll();
        assertEquals(getTestGenres(), actualGenres);
    }

    private List<Genre> getTestGenres() {
        return new ArrayList<>(Arrays.asList(
                new Genre(1, "вестерн"),
                new Genre(2, "ужасы")
        ));
    }

}