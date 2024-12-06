package org.acme.rebelrescue.fleet;


import org.acme.rebelrescue.fleet.api.AssembleAFleet;
import org.acme.rebelrescue.fleet.spi.stubs.StarshipInventoryStub;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

class AssembleAFleetFunctionalTest {

    @Test
    void should_assemble_a_fleet_for_1050_passengers() {
        // Given
        var numberOfPassengers = 1050;
        AssembleAFleet assembleAFleet = new FleetAssembler(new StarshipInventoryStub());
        // When
        Fleet fleet = assembleAFleet.forPassengers(numberOfPassengers);
        // Then
        assertThat(fleet.starships())
                .has(enoughCapacityForAllPassengers(numberOfPassengers))
                .allMatch(hasSomeCapacity());
    }

    private Predicate<? super Starship> hasSomeCapacity() {
        return starship -> starship.capacity() > 0;
    }

    private Condition<? super List<? extends Starship>> enoughCapacityForAllPassengers(int numberOfPassengers) {
        return new Condition<>(
                starships ->
                        starships.stream().mapToInt(Starship::capacity).sum() >= numberOfPassengers,
                "capacity check for all passengers"
        );
    }
}