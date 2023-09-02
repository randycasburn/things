package com.randycasburn.thing.controllers;

import com.randycasburn.thing.business.Thing;
import com.randycasburn.thing.services.DatabaseRequestResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
@Sql(scripts={"classpath:schema.sql", "classpath:data.sql"})
class EndToEndTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAllThingsTest() {
        String requestUrl = "/things";
        ResponseEntity<Thing[]> response = restTemplate.getForEntity(requestUrl, Thing[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().length);
    }

    @Test
    public void getById() {
        String requestUrlString = "/things/1";
        ResponseEntity<Thing> response = restTemplate.getForEntity(requestUrlString, Thing.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getId());
        assertEquals("Thing 1", response.getBody().getName());
    }

    @Test
    public void getByNonExistentId() {
        String requestUrlString = "/things/23";
        ResponseEntity<Thing> response = restTemplate.getForEntity(requestUrlString, Thing.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void addThing(){
        HttpEntity<Thing> request = new HttpEntity<>(new Thing(16, "Thingy"));
        String requestUrl = "/things";
        ResponseEntity<DatabaseRequestResult> response = restTemplate.postForEntity(requestUrl, request, DatabaseRequestResult.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getRowCount() );
    }

}
