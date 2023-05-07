package hu.soft4d.resource;

import hu.soft4d.model.Cleaning;
import hu.soft4d.service.CleaningService;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

import static hu.soft4d.resource.utils.Roles.USER_ROLE;

@Path("/api/haccp/storage/{storageId}/cleaning")

@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Cleaning", description = "Add and read cleanings.")
public class CleaningResource {
    @PathParam("storageId")
    private String storageId;

    @Inject
    CleaningService cleaningService;

    @Inject
    SecurityIdentity securityIdentity;

    @NoCache
    @GET
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Successful query",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = Cleaning.class))}
        ),
        @APIResponse(responseCode = "401", description = "Token expired",
            content = {@Content(mediaType = "application/json")}
        ),
        @APIResponse(responseCode = "403", description = "Forbidden",
            content = {@Content(mediaType = "application/json")}
        )
    })
    @Operation(summary = "List all cleanings for the specific storage.")
    @RolesAllowed(USER_ROLE)
    public List<Cleaning> listAll() {
        return cleaningService.getAll(storageId);
    }

    @NoCache
    @GET
    @Path("/last")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Successful query",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = Cleaning.class))}
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
    @Operation(summary = "Get last cleaning for the specific storage")
    @RolesAllowed(USER_ROLE)
    public Cleaning getLast() {
        return cleaningService.getLast(storageId);
    }

    @NoCache
    @GET
    @Path("/{id}")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Successful query",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = Cleaning.class))}
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
    @Operation(summary = "Find cleaning by ID.")
    @RolesAllowed(USER_ROLE)
    public Cleaning findById(@PathParam("id") String cleaningId) {
        return cleaningService.getOne(storageId, cleaningId);
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
    @RolesAllowed(USER_ROLE)
    @Operation(summary = "Administrate new cleaning.")
    public Response save(@Context UriInfo uriInfo) {
        String userName = securityIdentity.getPrincipal().getName();

        Cleaning cleaning = new Cleaning();

        cleaningService.save(storageId, userName, cleaning);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(cleaning.getId());
        return Response.created(builder.build()).build();
    }
}
