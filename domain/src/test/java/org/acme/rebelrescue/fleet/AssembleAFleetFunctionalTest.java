package org.acme.rebelrescue.fleet;


import org.acme.rebelrescue.fleet.api.AssembleAFleet;
import org.acme.rebelrescue.fleet.spi.stubs.StarshipInventoryStub;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

class AssembleAFleetFunctionalTest {

    @Test
    void should_assemble_a_fleet_for_1050_passengers() {
        // Given
        List<Starship> starships = List.of(
                new Starship("no passenger starship", 0, BigDecimal.valueOf(3000)),
                new Starship("xs", 10, BigDecimal.valueOf(300)),
                new Starship("s", 50, BigDecimal.valueOf(3_000)),
                new Starship("m", 100, BigDecimal.valueOf(30_000)),
                new Starship("l", 200, BigDecimal.valueOf(300_000)),
                new Starship("xl", 800, BigDecimal.valueOf(1_300_000)),
                new Starship("xxl", 2000, BigDecimal.valueOf(2_600_000))
        );
        var numberOfPassengers = 1050;
        AssembleAFleet assembleAFleet = new FleetAssembler(new StarshipInventoryStub(starships));
        // When
        Fleet fleet = assembleAFleet.forPassengers(numberOfPassengers);
        // Then
        assertThat(fleet.starships())
                .has(enoughCapacityForAllPassengers(numberOfPassengers))
                .allMatch(hasSomeCapacity());
    }

    private Predicate<? super Starship> hasSomeCapacity() {
        return starship -> starship.passengersCapacity() > 0;
    }

    private Condition<? super List<? extends Starship>> enoughCapacityForAllPassengers(int numberOfPassengers) {
        return new Condition<>(
                starships ->
                        starships.stream().mapToInt(Starship::passengersCapacity).sum() >= numberOfPassengers,
                "passengersCapacity check for all passengers"
        );
    }
}