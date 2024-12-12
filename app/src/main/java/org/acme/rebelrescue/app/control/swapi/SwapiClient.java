/*
 *                C O P Y R I G H T  (c) 2015
 *                        DEDALUS SPA
 *                    All Rights Reserved
 *
 *
 *      THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF
 *                        DEDALUS SPA
 *     The copyright notice above does not evidence any
 *    actual or intended publication of such source code.
 */
package org.acme.rebelrescue.app.control.swapi;

import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.rebelrescue.fleet.Starship;
import org.acme.rebelrescue.fleet.spi.StarshipInventory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@ApplicationScoped
public class SwapiClient implements StarshipInventory {
    private static final Logger LOG = LogManager.getLogger(SwapiClient.class);

    @RestClient
    SwapiService swapiService;

    @Startup
    void onStart() {
        LOG.info("Using swapi client");
    }

    @Override
    public List<Starship> starships() {
        var swapiResponse = getStarshipsFromSwapi(null);
        List<Starship> starships = new ArrayList<>(convertSwapiResponseToStarships(swapiResponse));
        String next;
        while ((next = swapiResponse.next()) != null) {
            int page = extractPageNumberFromUrl(next);
            swapiResponse = getStarshipsFromSwapi(page);
            starships.addAll(convertSwapiResponseToStarships(swapiResponse));
        }
        return starships;
    }

    /**
     * next value returned by Swapi provides a full URL (e.g. http://swapi/api/starships/?page=2) but we're only
     * interested in the page number, so let's hack this
     * @param nextUrl The next URL returned by swapi
     * @return the page number or null
     * @throws RuntimeException if the URL or the page query string argument are malformed
     */
    private Integer extractPageNumberFromUrl(String nextUrl) {
        if (nextUrl == null || nextUrl.isBlank()) {
            return null;
        }
        try {
            URI uri = new URI(nextUrl);
            String query = uri.getQuery();
            // transform query string into a map
            Map<String, String> queryParams = queryParamsAsMap(query);
            return Integer.parseInt(queryParams.get("page"));
        } catch (URISyntaxException | NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, String> queryParamsAsMap(String query) {
        return Arrays.stream(query.split("&"))
                .map(s -> s.split("="))
                .collect(toMap(a -> a[0], a -> a[1]));
    }

    private List<Starship> convertSwapiResponseToStarships(SwapiResponse swapiResponse) {
        return swapiResponse.results().stream()
                .filter(hasValidPassengersValue())
                .map(toStarShip())
                .collect(toList());
    }

    private Function<SwapiStarship, Starship> toStarShip() {
        return swapiStarShip ->
                new Starship(
                        swapiStarShip.name(),
                        parseInt(swapiStarShip.passengers().replaceAll(",", "")),
                        cargoCapacity(swapiStarShip.cargo_capacity()));
    }

    private BigDecimal cargoCapacity(String cargoCapacity) {
        BigDecimal capacity = BigDecimal.ZERO;
        if (cargoCapacity != null
                && !cargoCapacity.isBlank()
                && !cargoCapacity.equalsIgnoreCase("unknown")
                && !cargoCapacity.equalsIgnoreCase("n/a")
        ) {
            capacity = new BigDecimal(cargoCapacity.replaceAll(",", ""));
        }
        return capacity;
    }

    private Predicate<SwapiStarship> hasValidPassengersValue() {
        return swapiStarShip -> swapiStarShip.passengers() != null
                && !swapiStarShip.passengers().equalsIgnoreCase("n/a")
                && !swapiStarShip.passengers().equalsIgnoreCase("unknown");
    }

    private SwapiResponse getStarshipsFromSwapi(Integer page) {
        SwapiResponse response = swapiService.getStarships(page).getEntity();
        LOG.debug("swapi response {}", response);
        return response;
    }
}
