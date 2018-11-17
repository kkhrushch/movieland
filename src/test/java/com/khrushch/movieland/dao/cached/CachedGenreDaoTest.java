package com.khrushch.movieland.dao.cached;

import com.khrushch.movieland.dao.cached.CachedGenreDao;
import com.khrushch.movieland.model.Genre;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CachedGenreDaoTest {

    @Test
    public void getAll() throws NoSuchFieldException {
        CachedGenreDao cachedGenreDao = new CachedGenreDao();

        // set test cache
        Field genreCacheField = CachedGenreDao.class.getDeclaredField("genreCache");
        genreCacheField.setAccessible(true);
        ReflectionUtils.setField(genreCacheField, cachedGenreDao, getTestGenres());
        genreCacheField.setAccessible(false);

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