package hu.soft4d.resource;

import hu.soft4d.model.ControlPoint;
import hu.soft4d.service.ControlPointService;
import io.quarkus.security.Authenticated;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

import static hu.soft4d.resource.utils.Roles.ADMIN_ROLE;
import static hu.soft4d.resource.utils.Roles.USER_ROLE;

@Path("/api/haccp/storage/{storageId}/ccp")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "ControlPoint", description = "Managing critical control points.")
public class ControlPointResource {

    @PathParam("storageId")
    private String storageId;

    @Inject
    ControlPointService controlPointService;

    @NoCache
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Successful query",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = ControlPoint.class))}
        ),
        @APIResponse(responseCode = "401", description = "Token expired",
            content = {@Content(mediaType = "application/json")}
        ),
        @APIResponse(responseCode = "403", description = "Forbidden",
            content = {@Content(mediaType = "application/json")}
        )
    })
    @GET
    @Operation(summary = "List all critical control points.")
    @RolesAllowed(USER_ROLE)
    public List<ControlPoint> listAll() {
        return controlPointService.listAll(storageId);
    }

    @NoCache
    @GET
    @Path("{id}")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Successful query",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = ControlPoint.class))}
        ),
        @APIResponse(responseCode = "404", description = "Entity not found",
            content = {@Content(mediaType = "application/json")}
        ),
        @APIResponse(responseCode = "401", description = "Token expired",
            content = {@Content(mediaType = "application/json")}
        ),
        @APIResponse(responseCode = "403", description = "Forbidden",
            content = {@Content(mediaType = "application/json")}
        )
    })
    @Operation(summary = "Find critical control point by ID.")
    @RolesAllowed(USER_ROLE)
    public ControlPoint findById(@PathParam("id") String controlPointId) {
        return controlPointService.findById(storageId, controlPointId);
    }

    @POST
    @APIResponses(value = {
        @APIResponse(responseCode = "201", description = "Adding successful",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = Response.class))}
        ),
        @APIResponse(responseCode = "500", description = "Item already exists",
            content = {@Content(mediaType = "application/json")}
        ),
        @APIResponse(responseCode = "401", description = "Token expired",
            content = {@Content(mediaType = "application/json")}
        ),
        @APIResponse(responseCode = "403", description = "Forbidden",
            content = {@Content(mediaType = "application/json")}
        )
    })
    @Transactional(Transactional.TxType.REQUIRED)
    @NoCache
    @RolesAllowed(ADMIN_ROLE)
    @Operation(summary = "Create new storage.")
    public Response persist(ControlPoint controlPoint, @Context UriInfo uriInfo) {
        controlPointService.persist(storageId, controlPoint);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(controlPoint.getId());
        return Response.created(builder.build()).build();
    }


    @PUT
    @NoCache
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Modification successful",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = Response.class))}
        ),
        @APIResponse(responseCode = "500", description = "No such item",
            content = {@Content(mediaType = "application/json")}
        ),
        @APIResponse(responseCode = "401", description = "Token expired",
            content = {@Content(mediaType = "application/json")}
        ),
        @APIResponse(responseCode = "403", description = "Forbidden",
            content = {@Content(mediaType = "application/json")}
        )
    })
    @RolesAllowed(ADMIN_ROLE)
    @Operation(summary = "Update existing critical control point.")
    public Response update(ControlPoint controlPoint, @Context UriInfo uriInfo) {
        controlPointService.update(storageId, controlPoint);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(controlPoint.getId());
        return Response.created(builder.build()).build();
    }
}
