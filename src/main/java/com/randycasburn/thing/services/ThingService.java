package com.randycasburn.thing.services;

import com.randycasburn.thing.business.Thing;
import com.randycasburn.thing.integration.ThingDao;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThingService {

    @Autowired
    private Logger logger;

    @Autowired
    private ThingDao dao;

    public List<Thing> getAllthings() {
        List<Thing> things;
        things = dao.getAllThings();
        logger.info("Getting all Things");
        return things;
    }

    public Thing getThingById(int id) {
        logger.info("Getting one Thing by id");
        return dao.getThing(id);
    }

    public int removeThing(int id) {
        logger.info("removing a thing with id: " + id);
        return dao.deleteThing(id);
    }

    public int addThing(Thing thing) {
        logger.info("adding a Thing: " + thing.getName());
        return dao.insertThing(thing);
    }
}
