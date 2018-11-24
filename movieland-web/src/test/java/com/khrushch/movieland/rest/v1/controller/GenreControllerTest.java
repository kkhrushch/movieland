package com.khrushch.movieland.rest.v1.controller;

import com.khrushch.movieland.model.Genre;
import com.khrushch.movieland.service.GenreService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class GenreControllerTest {

    @Test
    public void getAll() {
        GenreService mockGenreService = mock(GenreService.class);
        when(mockGenreService.getAll()).thenReturn(getTestGenres());

        GenreController genreController = new GenreController();
        genreController.setGenreService(mockGenreService);

        List<Genre> actualGenres = genreController.getAll();

        assertEquals(getTestGenres(), actualGenres);
        verify(mockGenreService, times(1)).getAll();
    }

    private List<Genre> getTestGenres() {
        return new ArrayList<>(Arrays.asList(
                new Genre(1, "вестерн"),
                new Genre(2, "ужасы")
        ));
    }
}