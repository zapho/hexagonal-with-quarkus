package org.acme.rebelrescue.app.boundary;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.rebelrescue.fleet.Fleet;
import org.acme.rebelrescue.fleet.api.AssembleAFleet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.net.URISyntaxException;

@Path("/rescueFleets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RescueFleetResource {

    private static final Logger LOG = LogManager.getLogger(RescueFleetResource.class);

    private final AssembleAFleet assembleAFleet;

    public RescueFleetResource(AssembleAFleet assembleAFleet) {
        this.assembleAFleet = assembleAFleet;
    }

    @POST
    public Response assembleAFleet(RescueFleetRequest request) throws URISyntaxException {
        LOG.debug("Assembling a fleet from request {}", request);
        Fleet fleet = assembleAFleet.forPassengers(request.numberOfPassengers);
        URI url = new URI("");
        return Response.created(url).entity(fleet).build();
    }
}
