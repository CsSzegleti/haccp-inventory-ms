package hu.soft4d.resource;

import hu.soft4d.model.ControlProperty;
import hu.soft4d.service.ControlPropertyService;
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
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

import static hu.soft4d.resource.utils.Roles.USER_ROLE;

@Path("/api/haccp/storage/{storageId}/ccp/{ccpId}/property")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "ControlProperty", description = "Adding and reading properties for critical control point.")
public class ControlPropertyResource {
    @PathParam("storageId")
    private String storageId;

    @PathParam("ccpId")
    private String ccpId;

    @Inject
    ControlPropertyService controlPropertyService;

    @Inject
    SecurityIdentity securityIdentity;

    @NoCache
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Successful query",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = ControlProperty.class))}
        ),
        @APIResponse(responseCode = "401", description = "Token expired",
            content = {@Content(mediaType = "application/json")}
        ),
        @APIResponse(responseCode = "403", description = "Forbidden",
            content = {@Content(mediaType = "application/json")}
        )
    })
    @GET
    @Operation(summary = "List all properties for the critical control point.")
    @RolesAllowed(USER_ROLE)
    public List<ControlProperty> listAll() {
        return controlPropertyService.getAll(ccpId);
    }


    @GET
    @Path("/last")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Successful query",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = ControlProperty.class))}
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
    @Operation(summary = "Find latest property for the critical control point.")
    @RolesAllowed(USER_ROLE)
    public ControlProperty getLast() {
        return controlPropertyService.getLast(ccpId);
    }

    @GET
    @Path("/{id}")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Successful query",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = ControlProperty.class))}
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
    @Operation(summary = "Find  by ID.")
    @RolesAllowed(USER_ROLE)
    public ControlProperty findById(@PathParam("id") String controlPropertyId) {
        return controlPropertyService.getOne(ccpId, controlPropertyId);
    }

    @APIResponses(value = {
        @APIResponse(responseCode = "201", description = "Adding successful",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = Response.class))}
        ),
        @APIResponse(responseCode = "500", description = "Item already exists or no such ccp",
            content = {@Content(mediaType = "application/json")}
        ),
        @APIResponse(responseCode = "401", description = "Token expired",
            content = {@Content(mediaType = "application/json")}
        ),
        @APIResponse(responseCode = "403", description = "Forbidden",
            content = {@Content(mediaType = "application/json")}
        )
    })
    @POST
    @Transactional(Transactional.TxType.REQUIRED)
    @NoCache
    @RolesAllowed(USER_ROLE)
    @Operation(summary = "Administrate new property.")
    public Response persist(ControlProperty property, @Context UriInfo uriInfo) {
        String userName = securityIdentity.getPrincipal().getName();

        controlPropertyService.persist(storageId, ccpId, userName, property);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(property.getId());
        return Response.created(builder.build()).build();
    }


}
