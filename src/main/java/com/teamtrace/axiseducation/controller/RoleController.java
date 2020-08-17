package com.teamtrace.axiseducation.controller;

import com.teamtrace.axiseducation.api.request.Request;
import com.teamtrace.axiseducation.api.response.SearchResponse;
import com.teamtrace.axiseducation.service.RoleService;
import com.teamtrace.axiseducation.service.impl.RoleServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("roles")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Roles services.", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Request successful."),
        @ApiResponse(code = 204, message = "No data found."),
        @ApiResponse(code = 304, message = "Error occurred."),
        @ApiResponse(code = 401, message = "Unauthorized."),
        @ApiResponse(code = 500, message = "Error.")
})
public class RoleController extends AbstractController {
    private RoleService roleServiceImpl = new RoleServiceImpl();

    @Path("approved_list")
    @GET
    @ApiOperation(value = "Get approved roles.", httpMethod = "GET", response = SearchResponse.class)
    public Response getApprovedRoles(@Context HttpHeaders httpHeaders) {
        Request api = new Request();

        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processResponse(roleServiceImpl.getApprovedRolesByMerchantId(api));
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processResponse(roleServiceImpl.getApprovedRolesByMerchantId(api));
        }

        return Response.status(Response.Status.UNAUTHORIZED).entity("{}").build();
    }
}
