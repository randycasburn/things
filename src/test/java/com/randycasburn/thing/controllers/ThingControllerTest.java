package com.randycasburn.thing.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.randycasburn.thing.business.Thing;
import com.randycasburn.thing.integration.ThingDatabaseException;
import com.randycasburn.thing.services.ThingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ThingController.class)
public class ThingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ThingService mockService;

    private List<Thing> testThings;

    @BeforeEach
    void setUp(){
        testThings = List.of(new Thing(1, "Thing 1"),
                new Thing(2, "Thing 2"),
                new Thing(3, "Thing 3"));
    }

    @Test
    public void getAllThings() throws Exception {
        when(mockService.getAllthings()).thenReturn(testThings);
        mockMvc.perform(get("/things"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].id").value(testThings.get(0).getId()))
                .andExpect(jsonPath("$[0].name").value(testThings.get(0).getName()));

    }
    @Test
    public void getAllThings_Empty() throws Exception {
        when(mockService.getAllthings()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/things"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void getThingById() throws Exception {
        int id = 1;
        Thing testThing = new Thing(id, "Thing 1");
        when(mockService.getThingById(id)).thenReturn(testThing);

        mockMvc.perform(get("/things/1"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Thing 1"));

    }
    @Test
    public void getThingById_NULL() throws Exception {
        when(mockService.getThingById(1)).thenReturn(null);

        mockMvc.perform(get("/things/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void insertThing_Fail() throws Exception {
        int id = 1;
        when(mockService.addThing(testThings.get(0)))
                .thenReturn(0);
        mockMvc.perform(post("/things")
                .content(new ObjectMapper().writeValueAsString(testThings.get(0)))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }
    @Test
    public void getThingByIdNonExistent() throws Exception {
        int id = 888;
        when(mockService.getThingById(id))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND,
                "No Thing with id = " + id));

        mockMvc.perform(get("/things/" + id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
    @Test
    public void removeThing() throws Exception {
        int id = 1;
        when(mockService.removeThing(id))
                .thenReturn(1);
        mockMvc.perform(delete("/things/" + id))
                .andDo(print())
                .andExpect(status().isOk());

    }
    @Test
    public void removeUnKnownThing() throws Exception {
        int id = 444;
        when(mockService.removeThing(id))
                .thenReturn(0);
        mockMvc.perform(delete("/things/" + id))
                .andDo(print())
                .andExpect(status().isNotFound());

    }
    @Test
    public void databaseExceptions() throws Exception {
        when(mockService.removeThing(1)).thenThrow(new ThingDatabaseException());
        when(mockService.addThing(testThings.get(0))).thenThrow(new ThingDatabaseException());
        when(mockService.addThing(testThings.get(1))).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
        when(mockService.getThingById(1)).thenThrow(new ThingDatabaseException());
        when(mockService.getAllthings()).thenThrow(new ThingDatabaseException());
        mockMvc.perform(delete("/things/1"))
                .andExpect(status().is5xxServerError());
        mockMvc.perform(get("/things"))
                .andExpect(status().is5xxServerError());
        mockMvc.perform(get("/things/1"))
                .andExpect(status().is5xxServerError());
        mockMvc.perform(post("/things")
                        .content(new ObjectMapper().writeValueAsString(testThings.get(0)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
        mockMvc.perform(post("/things")
                        .content(new ObjectMapper().writeValueAsString(testThings.get(1)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
