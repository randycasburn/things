package com.randycasburn.thing.services;

import com.randycasburn.thing.business.Thing;
import com.randycasburn.thing.integration.ThingDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTableWhere;

@SpringBootTest
@Transactional
class ThingServiceIntegrationTest {
    @Autowired
    private ThingDao dao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private List<Thing> expectedthings = List.of(new Thing(1, "Thing 1"),
            new Thing(2, "Thing 2"),
            new Thing(3, "Thing 3"));

    @Test
    void getAllThings() {
        List<Thing> things = dao.getAllThings();
        assertEquals(expectedthings, things);
    }

    @Test
    void getThing() {
        int id = 1;
        Thing thing = dao.getThing(id);
        assertEquals(thing, new Thing(1, "Thing 1"));
    }

    @Test
    void deleteThing() {
        int countBefore = dao.getAllThings().size();
        dao.deleteThing(1);
        int countAfter = dao.getAllThings().size();
        assertEquals( countBefore - 1, countAfter);
        assertEquals(countRowsInTableWhere(jdbcTemplate, "things", "id = " + 1), 0);

    }

    @Test
    void insertThing() {
        Thing thing = new Thing(42, "Meaning of Life");
        dao.insertThing(thing);
        assertEquals(dao.getThing(42), thing);
        assertEquals(countRowsInTableWhere(jdbcTemplate, "things", "id = " + 42), 1);
    }
}
