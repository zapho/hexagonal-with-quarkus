package org.acme.rebelrescue.base;

import org.acme.rebelrescue.base.spi.BaseRepository;
import org.acme.rebelrescue.fleet.Fleet;
import org.acme.rebelrescue.fleet.Starship;
import org.acme.rebelrescue.hyperspace.api.ComputeTravelDuration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FleetDestinationFinderTest {

    private static final BigDecimal SOME_CARGO_CAPACITY = BigDecimal.ZERO;
    private static final int SOME_PASSENGER_CAPACITY = 10;

    @InjectMocks
    private FleetDestinationFinder destinationFinder;

    @Mock
    private BaseRepository baseRepository;

    @Mock
    private ComputeTravelDuration computeTravelDuration;

    @Test
    void should_return_empty_list_when_no_bases_match_risk_level() {
        when(baseRepository.findBaseByMaxRiskLevel(RiskLevel.SECURE.getLevel())).thenReturn(List.of());
        Fleet fleet = new Fleet(List.of(aStarship("starship 1")));

        List<RebelBase> bases = destinationFinder.findRebelBases(fleet, RiskLevel.SECURE, Duration.ofHours(3));

        assertThat(bases).isEmpty();
    }

    @Test
    void should_filter_out_bases_beyond_max_travel_duration() {
        RebelBase closeBase = new RebelBase("CloseBase", RiskLevel.SECURE, 1);
        RebelBase farBase = new RebelBase("FarBase", RiskLevel.SECURE, 5);
        when(baseRepository.findBaseByMaxRiskLevel(RiskLevel.SECURE.getLevel())).thenReturn(List.of(closeBase, farBase));
        when(computeTravelDuration.to(closeBase.name())).thenReturn(Duration.ofHours(2));
        when(computeTravelDuration.to(farBase.name())).thenReturn(Duration.ofHours(5));

        Fleet fleet = new Fleet(List.of(aStarship("starship 1")));

        List<RebelBase> bases = destinationFinder.findRebelBases(fleet, RiskLevel.SECURE, Duration.ofHours(3));

        assertThat(bases).hasSize(1);
        assertThat(bases.getFirst().name()).isEqualTo("CloseBase");
    }

    @Test
    void should_handle_fleet_with_multiple_risk_levels() {
        RebelBase secureBase = new RebelBase("SecureBase", RiskLevel.SECURE, 1);
        RebelBase elevatedBase = new RebelBase("ElevatedBase", RiskLevel.ELEVATED, 2);
        when(baseRepository.findBaseByMaxRiskLevel(RiskLevel.ELEVATED.getLevel())).thenReturn(List.of(secureBase, elevatedBase));
        when(computeTravelDuration.to(any(String.class))).thenReturn(Duration.ofHours(2));

        Fleet fleet = new Fleet(List.of(aStarship("starship 1")));

        List<RebelBase> bases = destinationFinder.findRebelBases(fleet, RiskLevel.ELEVATED, Duration.ofHours(3));

        assertThat(bases).hasSize(2);
        assertThat(bases).extracting("name").containsExactlyInAnyOrder("SecureBase", "ElevatedBase");
    }

    @Test
    void should_return_empty_list_when_no_starships_in_fleet() {
        Fleet emptyFleet = new Fleet(List.of());

        List<RebelBase> bases = destinationFinder.findRebelBases(emptyFleet, RiskLevel.SECURE, Duration.ofHours(3));

        assertThat(bases).isEmpty();
    }

    @Test
    void should_return_all_bases_when_all_are_within_travel_duration() {
        RebelBase base1 = new RebelBase("Base1", RiskLevel.SECURE, 1);
        RebelBase base2 = new RebelBase("Base2", RiskLevel.SECURE, 2);
        when(baseRepository.findBaseByMaxRiskLevel(0)).thenReturn(List.of(base1, base2));
        when(computeTravelDuration.to(any(String.class))).thenReturn(Duration.ofHours(1));

        Fleet fleet = new Fleet(List.of(aStarship("starship 1")));

        List<RebelBase> bases = destinationFinder.findRebelBases(fleet, RiskLevel.SECURE, Duration.ofHours(3));

        assertThat(bases).hasSize(2);
        assertThat(bases).extracting("name").containsExactlyInAnyOrder("Base1", "Base2");
    }

    @Test
    void should_handle_null_fleet_gracefully() {
        List<RebelBase> bases = destinationFinder.findRebelBases(null, RiskLevel.SECURE, Duration.ofHours(3));

        assertThat(bases).isEmpty();
    }

    @Test
    void should_handle_null_risk_level_gracefully() {
        Fleet fleet = new Fleet(List.of(aStarship("starship 1")));

        List<RebelBase> bases = destinationFinder.findRebelBases(fleet, null, Duration.ofHours(3));

        assertThat(bases).isEmpty();
    }

    @Test
    void should_handle_null_duration_gracefully() {
        Fleet fleet = new Fleet(List.of(aStarship("starship 1")));

        List<RebelBase> bases = destinationFinder.findRebelBases(fleet, RiskLevel.SECURE, null);

        assertThat(bases).isEmpty();
    }

    private static Starship aStarship(String name) {
        return new Starship(name, SOME_PASSENGER_CAPACITY, SOME_CARGO_CAPACITY);
    }}