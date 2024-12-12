package org.acme.rebelrescue.app;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class RescueFleetResourceEndpointTest {
    @Test
    void can_assemble_a_fleet_for_5_passengers() {
        given()
          .when()
                .contentType(MediaType.APPLICATION_JSON)
                .body("""
                    {"numberOfPassengers": 5}
                    """)
                .post("/rescueFleets")
          .then()
             .statusCode(Response.Status.CREATED.getStatusCode())
                .body("starships.size()", is(1))
                .body("starships[0].name", is("Millennium Falcon"))
                .body("starships[0].capacity", is(6));
    }
}