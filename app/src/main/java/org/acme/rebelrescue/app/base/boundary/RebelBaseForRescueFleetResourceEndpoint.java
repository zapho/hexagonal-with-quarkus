package org.acme.rebelrescue.app.base.boundary;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.rebelrescue.app.base.control.RebelBaseMapper;
import org.acme.rebelrescue.base.api.FindDestinationBaseForFleet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

@Path("/rebelBases/rescueCandidates")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RebelBaseForRescueFleetResourceEndpoint {

    private static final Logger LOG = LogManager.getLogger(RebelBaseForRescueFleetResourceEndpoint.class);

    private final FindDestinationBaseForFleet findDestinationBaseForFleet;
    private final RebelBaseMapper rebelBaseMapper;

    public RebelBaseForRescueFleetResourceEndpoint(
            FindDestinationBaseForFleet findDestinationBaseForFleet,
            RebelBaseMapper rebelBaseMapper) {
        this.findDestinationBaseForFleet = findDestinationBaseForFleet;
        this.rebelBaseMapper = rebelBaseMapper;
    }

    @POST
    public Response findBaseForFleet(FleetToRescueRequest request) {
        LOG.debug("Finding destination base for fleet request {}", request);
        var destinationBase = findDestinationBaseForFleet.findRebelBases(
                rebelBaseMapper.toFleetDomain(request.starshipNames()),
                rebelBaseMapper.toRiskLevelDomain(request.riskLevel()),
                Duration.ofHours(request.maxDurationInHours()));
        return Response.ok(rebelBaseMapper.toRebelBaseResources(destinationBase)).build();
    }
}
