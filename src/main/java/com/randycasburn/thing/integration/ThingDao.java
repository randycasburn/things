package com.randycasburn.thing.integration;

import com.randycasburn.thing.business.Thing;

import java.util.List;

public interface ThingDao {
    // Thing methods
    List<Thing> getAllThings();

    Thing getThing(int id);

    int deleteThing(int id);

    int insertThing(Thing w);

}
