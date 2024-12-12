package org.acme.rebelrescue.app.base.boundary;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.rebelrescue.app.base.control.RebelBaseManager;
import org.acme.rebelrescue.app.base.control.RebelBaseMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Path("/rebelBases")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RebelBaseResourceEndpoint {

    private static final Logger LOG = LogManager.getLogger(RebelBaseResourceEndpoint.class);

    private final RebelBaseManager rebelBaseManager;
    private final RebelBaseMapper rebelBaseMapper;

    public RebelBaseResourceEndpoint(
            RebelBaseManager rebelBaseManager,
            RebelBaseMapper rebelBaseMapper) {
        this.rebelBaseManager = rebelBaseManager;
        this.rebelBaseMapper = rebelBaseMapper;
    }

    @GET
    public Response getAllRebelBases() {
        LOG.debug("Getting all rebel bases");
        var rebelBases = rebelBaseManager.getAllRebelBases();
        return Response.ok(rebelBaseMapper.toRebelBaseResources(rebelBases)).build();
    }

    @POST
    public Response createRebelBase(CreateRebelBaseRequest request) {
        LOG.debug("Creating rebel base from request {}", request);
        var rebelBase = rebelBaseMapper.toRebelBaseDomain(request);
        rebelBaseManager.createRebelBase(rebelBase);
        return Response.accepted().build();
    }
}
