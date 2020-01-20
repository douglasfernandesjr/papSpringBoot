package com.example.project.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.convention.MatchingStrategies;

@RunWith(MockitoJUnitRunner.class)
public class MapperConfigTest {
    @Test
    public void shouldGetMapper() {
        MapperConfig mapper = new MapperConfig();
        assertNotNull("Mapper não pode ser null!", mapper.getModelMapper());
    }

    @Test
    public void shouldGetMapperWithSTRICT_MatchingStrategies() {
        MapperConfig mapper = new MapperConfig();
        assertEquals("Matching Strategy não esperada!",
                mapper.getModelMapper().getConfiguration().getMatchingStrategy(), MatchingStrategies.STRICT);
    }
}