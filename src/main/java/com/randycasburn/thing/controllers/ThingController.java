package com.randycasburn.thing.controllers;

import com.randycasburn.thing.business.RowCount;
import com.randycasburn.thing.business.Thing;
import com.randycasburn.thing.services.ThingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/things")
public class ThingController {
    @Autowired
    private ThingService service;

    @GetMapping
    public ResponseEntity<List<Thing>> getAllthings() {
        List<Thing> things = service.getAllthings();
        return things.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(things);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Thing> getThingById(@PathVariable int id) {
        Thing thing = service.getThingById(id);
        return thing == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(thing);
    }

    @DeleteMapping(value="/{id}", produces = "application/json")
    public ResponseEntity<RowCount> removeThing(@PathVariable("id") int id) {
        int rows = service.removeThing(id);
        return  rows == 0
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(new RowCount(rows));
    }

    @PostMapping
    public ResponseEntity<RowCount> insertThing(@RequestBody Thing thing) {
        int rows = service.addThing(thing);
        return  rows == 0
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(new RowCount(rows));
    }

}
