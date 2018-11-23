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
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CachedGenreDaoTest {

    @Test
    public void testGetAll() {
        CachedGenreDao cachedGenreDao = new CachedGenreDao();

        GenreDao mockGenreDao = mock(GenreDao.class);
        when(mockGenreDao.getAll()).thenReturn(getTestGenres());

        cachedGenreDao.setGenreDao(mockGenreDao);
        cachedGenreDao.refreshCache();

        List<Genre> actualGenres = cachedGenreDao.getAll();

        assertEquals(getTestGenres(), actualGenres);
        verify(mockGenreDao, times(1)).getAll();
    }

    @Test
    public void testGetByMovieId() {
        CachedGenreDao cachedGenreDao = new CachedGenreDao();

        GenreDao mockGenreDao = mock(GenreDao.class);
        when(mockGenreDao.getByMovieId(1)).thenReturn(getTestGenres());

        cachedGenreDao.setGenreDao(mockGenreDao);

        List<Genre> actualGenres = cachedGenreDao.getByMovieId(1);

        assertEquals(getTestGenres(), actualGenres);
        verify(mockGenreDao, times(1)).getByMovieId(1);
    }

    private List<Genre> getTestGenres() {
        return new ArrayList<>(Arrays.asList(
                new Genre(1, "вестерн"),
                new Genre(2, "ужасы")
        ));
    }
}