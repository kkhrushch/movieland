package com.khrushch.movieland.dao.cached;

import com.khrushch.movieland.dao.GenreDao;
import com.khrushch.movieland.model.Genre;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CachedGenreDaoTest {

    @Test
    public void getAll() {
        CachedGenreDao cachedGenreDao = new CachedGenreDao();

        GenreDao genreDao = mock(GenreDao.class);
        when(genreDao.getAll()).thenReturn(getTestGenres());

        cachedGenreDao.setGenreDao(genreDao);
        cachedGenreDao.refreshCache();

        List<Genre> actualGenres = cachedGenreDao.getAll();

        assertEquals(getTestGenres(), actualGenres);
    }

    private List<Genre> getTestGenres() {
        return new ArrayList<>(Arrays.asList(
                new Genre(1, "вестерн"),
                new Genre(2, "ужасы")
        ));
    }
}