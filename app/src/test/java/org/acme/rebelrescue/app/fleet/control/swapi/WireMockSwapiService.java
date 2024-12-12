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
package org.acme.rebelrescue.app.fleet.control.swapi;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;


public class WireMockSwapiService implements QuarkusTestResourceLifecycleManager {
    private static final Logger LOG = LogManager.getLogger(WireMockSwapiService.class);

    private WireMockServer wireMockServer;

    @Override
    public Map<String, String> start() {
        LOG.info("## Start WireMock");
        wireMockServer = new WireMockServer(8090);
        wireMockServer.start();

        wireMockServer.stubFor(get(urlEqualTo("/api/starships"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(fromFile("payloads/swapi-page1.json"))));

        wireMockServer.stubFor(get(urlEqualTo("/api/starships?page=2"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(fromFile("payloads/swapi-page2.json"))));

        return Map.of("quarkus.rest-client.\"org.acme.rebelrescue.app.fleet.control.swapi.SwapiService\".url", wireMockServer.baseUrl());
    }

    @Override
    public void stop() {
        LOG.info("## Stop WireMock");
        if (null != wireMockServer) {
            wireMockServer.stop();
        }
    }

    private String fromFile(String file) {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(file);
        if (resourceAsStream == null) {
            throw new IllegalArgumentException("file is not found!");
        }
        try {
            byte[] bytes = resourceAsStream.readAllBytes();
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
