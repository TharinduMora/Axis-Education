package com.teamtrace.axiseducation.controller;

import com.teamtrace.axiseducation.api.request.SearchCriteria;
import com.teamtrace.axiseducation.api.request.SystemParameterCreateRequest;
import com.teamtrace.axiseducation.api.request.SystemParameterUpdateRequest;
import com.teamtrace.axiseducation.api.response.CreationResponse;
import com.teamtrace.axiseducation.api.response.UpdateResponse;
import com.teamtrace.axiseducation.search.systemparameter.SystemParameterSearch;
import com.teamtrace.axiseducation.service.SystemParameterService;
import com.teamtrace.axiseducation.service.impl.SystemParameterServiceImpl;
import io.swagger.annotations.*;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("system_parameters")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "System parameters services.", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Request successful."),
        @ApiResponse(code = 204, message = "No data found."),
        @ApiResponse(code = 304, message = "Error occurred."),
        @ApiResponse(code = 401, message = "Unauthorized."),
        @ApiResponse(code = 500, message = "Error.")
})
public class SystemParameterController extends AbstractController {

    private final SystemParameterService systemParameterServiceImpl = new SystemParameterServiceImpl();
    private final SystemParameterSearch systemParameterSearch = new SystemParameterSearch();

    @POST
    @ApiOperation(value = "Create system parameter.", httpMethod = "POST", response = CreationResponse.class)
    public Response createSystemParameter(@Context HttpHeaders httpHeaders,
                                          @ApiParam @Valid SystemParameterCreateRequest api) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("{}").build();
        }

        return processResponse(systemParameterServiceImpl.createSystemParameter(api));
    }

    @PUT
    @ApiOperation(value = "Update system parameter.", httpMethod = "PUT", response = UpdateResponse.class)
    public Response updateSystemParameter(@Context HttpHeaders httpHeaders,
                                          @ApiParam @Valid SystemParameterUpdateRequest api) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("{}").build();
        }

        return processResponse(systemParameterServiceImpl.updateSystemParameter(api));
    }

    @Path("find_by_criteria")
    @POST
    @ApiOperation(value = "Find system parameters by criteria.", httpMethod = "POST", response = CreationResponse.class)
    public Response findSystemParametersByCriteria(@Context HttpHeaders httpHeaders,
                                                   @ApiParam @Valid SearchCriteria api) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("{}").build();
        }

        return processSearchResponse(systemParameterSearch.findByCriteria(api));
    }
}
