package com.randycasburn.thing.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ThingTest {

    @Test
    public void testEquals_Symmetric() {
        Thing x = new Thing(1,"a");
        Thing y = new Thing(1, "a");
        Thing z = new Thing(1, "b");
        assertTrue(x.equals(y) && y.equals(x));
        assertEquals(x.hashCode(), y.hashCode());
        assertFalse(x.equals(z) && z.equals(x));
    }

    @Test
    void testToString() {
        assertEquals("1| Thing 1", new Thing(1,"Thing 1").toString());
    }
}
