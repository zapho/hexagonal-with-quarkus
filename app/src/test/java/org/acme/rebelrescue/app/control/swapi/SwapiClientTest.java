package org.acme.rebelrescue.app.control.swapi;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.acme.rebelrescue.fleet.Starship;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@QuarkusTestResource(value = WireMockSwapiService.class, restrictToAnnotatedClass = true) // restrictToAnnotatedClass=true : otherwise wiremock is started for integration tests as well
class SwapiClientTest {

    @Inject
    SwapiClient client;

    @Test
    void should_return_full_starship_inventory() {
        List<Starship> starships = client.starships();
        assertThat(starships).containsExactly(
                new Starship("CR90 corvette", 600),
                new Starship("Slave 1", 6),
                new Starship("Death Star", 843342));
    }
}