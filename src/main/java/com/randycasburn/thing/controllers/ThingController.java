package com.randycasburn.thing.controllers;

import com.randycasburn.thing.business.Thing;
import com.randycasburn.thing.integration.ThingDatabaseException;
import com.randycasburn.thing.services.DatabaseRequestResult;
import com.randycasburn.thing.services.ThingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerErrorException;

import java.util.List;

@RestController
@RequestMapping("/things")
public class ThingController {
    private static final String DB_ERROR_MSG =
            "Error communicating with the database";

    @Autowired
    private ThingService service;

    @GetMapping
    public ResponseEntity<List<Thing>> getAllthings() {
        ResponseEntity<List<Thing>> result;
        List<Thing> things;
        try {
            things = service.getAllthings();
        } catch (ThingDatabaseException e) {
            throw new ServerErrorException(DB_ERROR_MSG, e);
        }

        if (things.size() > 1) {
            result = ResponseEntity.ok(things);
        } else {
            result = ResponseEntity.noContent().build();
        }
        return result;
    }

    @GetMapping("/{id}")
    public Thing getThingById(@PathVariable int id) {
        Thing thing = null;
        try {
            thing = service.getThingById(id);
        } catch (ThingDatabaseException e) {
            throw new ServerErrorException(DB_ERROR_MSG, e);
        }
        if (thing == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No Thing with id = " + id);
        }
        return thing;
    }

    @DeleteMapping(value="/{id}", produces = "application/json")
    public DatabaseRequestResult removeThing(@PathVariable("id") int id) {
        int rows = 0;
        try {
            rows = service.removeThing(id);
        } catch (ThingDatabaseException e) {
            throw new ServerErrorException(DB_ERROR_MSG, e);
        }
        if (rows == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No Thing with id = " + id);
        }
        return new DatabaseRequestResult(rows);
    }

    @PostMapping
    public DatabaseRequestResult insertThing(@RequestBody Thing w) {
        int count = 0;
        try {
            count = service.addThing(w);
        } catch (ThingDatabaseException e) {
            throw new ServerErrorException(DB_ERROR_MSG, e);
        }
        if (count == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return new DatabaseRequestResult(count);
    }

}
