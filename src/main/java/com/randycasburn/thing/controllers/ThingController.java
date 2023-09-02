package com.randycasburn.thing.controllers;

import com.randycasburn.thing.business.Thing;
import com.randycasburn.thing.integration.ThingDatabaseException;
import com.randycasburn.thing.business.RowCount;
import com.randycasburn.thing.services.ThingService;
import org.slf4j.Logger;
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
    Logger logger;

    @Autowired
    private ThingService service;

    @GetMapping
    public ResponseEntity<List<Thing>> getAllthings() {
        ResponseEntity<List<Thing>> result;
        List<Thing> things;
        try {
            things = service.getAllthings();
            logger.info("Getting all Things");
        } catch (ThingDatabaseException e) {
            logger.error(DB_ERROR_MSG);
            throw new ServerErrorException(DB_ERROR_MSG, e);
        }

        if (things.size() > 1) {
            result = ResponseEntity.ok(things);
        } else {
            logger.warn("Things list is empty!");
            result = ResponseEntity.noContent().build();
        }
        return result;
    }

    @GetMapping("/{id}")
    public Thing getThingById(@PathVariable int id) {
        Thing thing = null;
        try {
            thing = service.getThingById(id);
            logger.info("Getting one Thing by id");
        } catch (ThingDatabaseException e) {
            logger.error(DB_ERROR_MSG);
            throw new ServerErrorException(DB_ERROR_MSG, e);
        }
        if (thing == null) {
            logger.error("Thing is null from getThingById(" + id + ")");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No Thing with id = " + id);
        }
        return thing;
    }

    @DeleteMapping(value="/{id}", produces = "application/json")
    public RowCount removeThing(@PathVariable("id") int id) {
        int rows = 0;
        try {
            rows = service.removeThing(id);
            logger.info("removing a thing");
        } catch (ThingDatabaseException e) {
            logger.error(DB_ERROR_MSG);
            throw new ServerErrorException(DB_ERROR_MSG, e);
        }
        if (rows == 0) {
            logger.error("Did not remove from removeThing(" + id + ")");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No Thing with id = " + id);
        }
        return new RowCount(rows);
    }

    @PostMapping
    public RowCount insertThing(@RequestBody Thing w) {
        int count = 0;
        try {
            count = service.addThing(w);
            logger.info("adding a Thing");
        } catch (ThingDatabaseException e) {
            logger.error(DB_ERROR_MSG);
            throw new ServerErrorException(DB_ERROR_MSG, e);
        }
        if (count == 0) {
            logger.error("Did not insert from removeThing() Name:" +  w.getName());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return new RowCount(count);
    }

}
