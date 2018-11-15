package com.khrushch.movieland.service;

import com.khrushch.movieland.model.Genre;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GenreCacheServiceTest {

    @Test
    public void getAll() throws NoSuchFieldException {
        GenreCacheService genreCacheService = new GenreCacheService();

        // set test cache
        Field genreCacheField = GenreCacheService.class.getDeclaredField("genreCache");
        genreCacheField.setAccessible(true);
        ReflectionUtils.setField(genreCacheField, genreCacheService, getTestGenres());
        genreCacheField.setAccessible(false);

        List<Genre> actualGenres = genreCacheService.getAll();

        assertEquals(getTestGenres(), actualGenres);
    }

    private List<Genre> getTestGenres() {
        return new ArrayList<>(Arrays.asList(
                new Genre(1, "вестерн"),
                new Genre(2, "ужасы")
        ));
    }
}