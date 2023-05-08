package hu.soft4d.resource;

import hu.soft4d.model.FoodStorage;
import hu.soft4d.resource.dto.InventoryItemToMoveDto;
import hu.soft4d.service.FoodStorageService;
import io.quarkus.security.Authenticated;
import org.apache.commons.beanutils.BeanUtils;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import static hu.soft4d.resource.utils.Roles.ADMIN_ROLE;
import static hu.soft4d.resource.utils.Roles.USER_ROLE;

@Path("/api/v1/storage")
//@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "FoodStorage", description = "Managing storages")
public class FoodStorageResource {

    @Inject
    FoodStorageService foodStorageService;

    @NoCache
    @GET
    @Path("{id}")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Successful query",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = FoodStorage.class))}
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
    @Operation(summary = "Find storage by ID.", operationId = "findFoodStorageById")
    //@RolesAllowed(USER_ROLE)
    public FoodStorage findFoodStorageById(@PathParam("id") String id) {
        return foodStorageService.findByIdOptional(id).orElseThrow(NotFoundException::new);
    }

    @NoCache
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Successful query",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = FoodStorage.class))}
        ),
        @APIResponse(responseCode = "401", description = "Token expired",
            content = {@Content(mediaType = "application/json")}
        ),
        @APIResponse(responseCode = "403", description = "Forbidden",
            content = {@Content(mediaType = "application/json")}
        )
    })
    @GET
    @Operation(summary = "List all storages.", operationId = "listAllFoodStorages")
    //@RolesAllowed(USER_ROLE)
    public List<FoodStorage> listAllStorages() {
        return foodStorageService.listAll();
    }

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
    @POST
    @Transactional(Transactional.TxType.REQUIRED)
    @NoCache
//    @RolesAllowed(ADMIN_ROLE)
    @Operation(summary = "Create new storage.", operationId = "addNewFoodStorage")
    public Response persistFoodStorage(FoodStorage foodStorage, @Context UriInfo uriInfo) {
        foodStorageService.persist(foodStorage);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(foodStorage.getId());
        return Response.created(builder.build()).build();
    }

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
    @PUT
    @Path("{id}")
    @Transactional
    @NoCache
    //@RolesAllowed(ADMIN_ROLE)
    @Operation(summary = "Update existing food storage.", operationId = "updateFoodStorage")
    public Response updateFoodStorage(FoodStorage foodStorage, @PathParam("id") String id, @Context UriInfo uriInfo) throws InvocationTargetException, IllegalAccessException {
        if (!id.equals(foodStorage.getId())) {
            throw new BadRequestException();
        }

        Optional<FoodStorage> entity = foodStorageService.findByIdOptional(foodStorage.getId());
        if (entity.isEmpty()) {
            throw new NotFoundException();
        }

        BeanUtils.copyProperties(entity.get(), foodStorage);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        return Response.ok(builder.build()).build();
    }


    @NoCache
    @APIResponses(value = {
        @APIResponse(responseCode = "204", description = "Delete successful",
            content = {@Content(mediaType = "application/json")}
        ),
        @APIResponse(responseCode = "401", description = "Token expired",
            content = {@Content(mediaType = "application/json")}
        ),
        @APIResponse(responseCode = "403", description = "Forbidden",
            content = {@Content(mediaType = "application/json")}
        )
    })
    @DELETE
    @Transactional
    @Path("{id}")
    //@RolesAllowed(ADMIN_ROLE)
    @Operation(summary = "Delete food storage by ID.", operationId = "deleteFoodStorageById")
    public void deleteFoodStorage(@PathParam("id") String id) {
        foodStorageService.deleteById(id);
    }

    @POST
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Adding item successful",
                content = {@Content(mediaType = MediaType.TEXT_PLAIN)}),
            @APIResponse(responseCode = "404", description = "No such storage",
                content = {@Content(mediaType = "application/json")}),
            @APIResponse(responseCode = "401", description = "Token expired",
                content = {@Content(mediaType = "application/json")}),
            @APIResponse(responseCode = "403", description = "Forbidden",
                content = {@Content(mediaType = "application/json")})
    })
    @Path("{id}/add_item")
    //@RolesAllowed(USER_ROLE)
    @Operation(summary = "Add menu item to storage using its ID. Returns", operationId = "addMenuItemToFoodStorage")
    public List<String> AddInventoryItem(@PathParam("id") String storageId, InventoryItemToMoveDto itemToAdd) {
        return foodStorageService.addMultipleItemsToStorage(storageId, itemToAdd);
    }

    @POST
    @Path("{id}/remove_item")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Remove item successful",
            content = {@Content(mediaType = "application/json")}
        ),
        @APIResponse(responseCode = "404", description = "No such storage",
            content = {@Content(mediaType = "application/json")}
        ),
        @APIResponse(responseCode = "401", description = "Token expired",
            content = {@Content(mediaType = "application/json")}
        ),
        @APIResponse(responseCode = "403", description = "Forbidden",
            content = {@Content(mediaType = "application/json")}
        )
    })
    //@RolesAllowed(USER_ROLE)
    @Operation(summary = "Remove menu item from storage.", operationId = "removeMenuItemFromFoodStorage")
    public List<String> RemoveInventoryItem(@PathParam("id") String storageId, InventoryItemToMoveDto itemToRemove) {
        return foodStorageService.removeMultipleItemsFromStorage(storageId, itemToRemove);
    }
}
