package com.randycasburn.thing.integration;

import com.randycasburn.thing.business.Thing;
import com.randycasburn.thing.integration.mapper.ThingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("thingdao")
public class ThingDaoMyBatisImpl implements ThingDao{
        @Autowired
        private ThingMapper mapper;

        @Override
        public List<Thing> getAllThings() {
            return mapper.getAllThings();
        }

        @Override
        public Thing getThing(int id) {
            return mapper.getThing(id);
        }

        @Override
        public int deleteThing(int id) {
            return mapper.deleteThing(id);
        }

        @Override
        public int insertThing(Thing w) {
            return mapper.insertThing(w);
        }
    }
