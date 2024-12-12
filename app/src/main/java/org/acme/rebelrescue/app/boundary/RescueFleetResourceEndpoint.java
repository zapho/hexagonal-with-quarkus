package org.acme.rebelrescue.app.boundary;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.rebelrescue.app.control.FleetMapper;
import org.acme.rebelrescue.fleet.Fleet;
import org.acme.rebelrescue.fleet.api.AssembleAFleet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.net.URISyntaxException;

@Path("/rescueFleets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RescueFleetResourceEndpoint {

    private static final Logger LOG = LogManager.getLogger(RescueFleetResourceEndpoint.class);

    private final AssembleAFleet assembleAFleet;
    private final FleetMapper fleetMapper;

    public RescueFleetResourceEndpoint(AssembleAFleet assembleAFleet, FleetMapper fleetMapper) {
        this.assembleAFleet = assembleAFleet;
        this.fleetMapper = fleetMapper;
    }

    @POST
    public Response assembleAFleet(RescueFleetRequest request) throws URISyntaxException {
        LOG.debug("Assembling a fleet from request {}", request);
        Fleet fleet = assembleAFleet.forPassengers(request.numberOfPassengers);
        FleetResource fleetResource = fleetMapper.toFleetResource(fleet);
        URI url = new URI("");
        return Response.created(url).entity(fleetResource).build();
    }
}
