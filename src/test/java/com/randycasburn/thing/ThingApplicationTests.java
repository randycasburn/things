package com.randycasburn.thing;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ThingApplicationTests {

    @Test
    public void testMain() {
        ThingApplication.main(new String[] {});
        assertTrue(true);
    }
}
