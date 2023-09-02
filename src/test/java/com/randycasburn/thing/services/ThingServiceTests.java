package com.randycasburn.thing.services;

import com.randycasburn.thing.business.Thing;
import com.randycasburn.thing.controllers.ThingController;
import com.randycasburn.thing.integration.ThingDao;
import com.randycasburn.thing.integration.ThingDatabaseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ThingServiceTests {
    @Mock
    private Logger logger;

    @Mock
    private ThingDao mockDao;

    @InjectMocks
    private ThingService service;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllthings() {
        List<Thing> things = List.of(new Thing(1, "Thing 1"), new Thing(2, "Thing 2"));
        when(mockDao.getAllThings()).thenReturn(things);
        List<Thing> allThings = service.getAllthings();
        assertEquals(things, allThings);
    }
    @Test
    void getAllthingsEmptyReturn() {
        when(mockDao.getAllThings()).thenReturn(new ArrayList<>());
        List<Thing> allThings = service.getAllthings();
        assertEquals(0, allThings.size());
    }

    @Test
    void getAllthingsThrows() {
        when(mockDao.getAllThings()).thenThrow(new ThingDatabaseException());
        assertThrows(ThingDatabaseException.class, ()-> { service.getAllthings();});
    }
    @Test
    void getThingById() {
        int id = 44;
        when(mockDao.getThing(id)).thenReturn(new Thing(id, "mock"));
        assertEquals(id, service.getThingById(id).getId());
    }

    @Test
    void removeThing() {
        when(mockDao.deleteThing(1)).thenReturn(1);
        assertEquals(1, service.removeThing(1));
    }

    @Test
    void addThing() {
        Thing thing = new Thing(33, "mock");
        when(mockDao.insertThing(thing)).thenReturn(1);
        assertEquals(1, service.addThing(thing));
    }

    @Test
    void addThingDuplicateKeyException(){
        int id = 1;
        Thing thing = new Thing(33, "mock");
        when(mockDao.insertThing(thing)).thenThrow(new DuplicateKeyException("mock exception"));
        assertThrows(DuplicateKeyException.class, ()->{ service.addThing(thing);});
    }
}
