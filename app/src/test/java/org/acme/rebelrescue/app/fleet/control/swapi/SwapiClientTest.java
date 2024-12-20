package org.acme.rebelrescue.app.fleet.control.swapi;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.acme.rebelrescue.fleet.Starship;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import java.math.BigDecimal;
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
                new Starship("CR90 corvette", 600, BigDecimal.valueOf(3000000)),
                new Starship("Slave 1", 6, BigDecimal.valueOf(70000)),
                new Starship("Death Star", 843342, BigDecimal.valueOf(1000000000000L)));
    }
}