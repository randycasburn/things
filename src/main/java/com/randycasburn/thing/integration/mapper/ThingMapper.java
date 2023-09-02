package com.randycasburn.thing.integration.mapper;

import com.randycasburn.thing.business.Thing;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ThingMapper {
        // ***** Thing Methods *****
        List<Thing> getAllThings();
        Thing getThing(int id);
        int deleteThing(int id);
        int insertThing(Thing Thing);
}
