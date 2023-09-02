package com.randycasburn.thing.services;

import com.randycasburn.thing.business.Thing;
import com.randycasburn.thing.integration.ThingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThingService {
    @Autowired
    ThingDao dao;
    public List<Thing> getAllthings() {
        return dao.getAllThings();
    }

    public Thing getThingById(int id) {
        return dao.getThing(id);
    }

    public int removeThing(int id) {
        return dao.deleteThing(id);
    }

    public int addThing(Thing thing) {
        return dao.insertThing(thing);
    }
}
