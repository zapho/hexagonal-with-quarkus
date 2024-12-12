package org.acme.rebelrescue.app;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusIntegrationTest
class RescueFleetResourceEndpointIT {
    @Test
    void can_assemble_a_fleet_for_3_passengers() {
        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .body("""
                    {"numberOfPassengers": 3}
                    """)
                .post("/rescueFleets")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .body("starships.size()", is(1))
                .body("starships[0].name", is("Naboo star skiff"))
                .body("starships[0].capacity", is(3));
    }
}
