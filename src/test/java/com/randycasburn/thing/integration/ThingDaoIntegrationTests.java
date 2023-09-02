package com.randycasburn.thing.integration;

import com.randycasburn.thing.business.Thing;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.jdbc.JdbcTestUtils.*;

@SpringBootTest
@Transactional
public class ThingDaoIntegrationTests {
    @Autowired
    private ThingDao dao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static List<Thing> testThings = List.of(new Thing(1, "Thing 1"),
            new Thing(2, "Thing 2"),
            new Thing(3, "Thing 3"));

    @Test
    void getAllThings(){
        List<Thing> things = dao.getAllThings();
        assertEquals(things,testThings);
    }
    @Test
    void getAllThingsEmptyTable(){
        deleteFromTables(jdbcTemplate, "things");
        List<Thing> things = dao.getAllThings();
        assertEquals(things.size(), 0);
    }
    @Test
    void getThing(){
        Thing thing = dao.getThing(1);
        assertEquals(thing.getId(), 1);
    }

    @Test
    void removeThing(){
        int countBefore = countRowsInTable(jdbcTemplate, "things");
        dao.deleteThing(1);
        int countAfter = countRowsInTable(jdbcTemplate, "things");
        assertNull(dao.getThing(1));
        assertEquals(countAfter, countBefore - 1);
        assertEquals(countRowsInTableWhere(jdbcTemplate, "things", "id = 1"), 0);
    }

    @Test
    void removeThingNotPresent(){
        int countBefore = countRowsInTable(jdbcTemplate, "things");
        int removedCount = dao.deleteThing(999);
        assertEquals(0, removedCount);
        int countAfter = countRowsInTable(jdbcTemplate, "things");
        assertNull(dao.getThing(999));
        assertEquals(countBefore, countAfter);
    }
    @Test
    void insertThing(){
        int id = 111;
        int countBefore = countRowsInTableWhere(jdbcTemplate, "things", "id = 111");
        assertEquals(0, countBefore);
        int addCount = dao.insertThing(new Thing(id, "mock"));
        assertEquals(1, addCount);
       assertEquals(1, countRowsInTableWhere(jdbcTemplate, "things", "id=111 and name='mock'"));
    }
    @Test
    void insertThingDuplicateId(){
        int id = 111;
        int addCount = dao.insertThing(new Thing(id, "mock"));
        assertEquals(1, addCount);
        assertThrows(DuplicateKeyException.class, ()-> { dao.insertThing(new Thing(id, "mock"));});
    }

}
