package com.teamtrace.axiseducation.controller;

import com.teamtrace.axiseducation.api.request.*;
import com.teamtrace.axiseducation.api.response.CreationResponse;
import com.teamtrace.axiseducation.api.response.UpdateResponse;
import com.teamtrace.axiseducation.search.rolegroup.RoleGroupListSearch;
import com.teamtrace.axiseducation.search.rolegroup.RoleGroupSearch;
import com.teamtrace.axiseducation.service.RoleGroupService;
import com.teamtrace.axiseducation.service.impl.RoleGroupServiceImpl;
import io.swagger.annotations.*;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("role_groups")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Admin management services.", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Request successful."),
        @ApiResponse(code = 204, message = "No data found."),
        @ApiResponse(code = 304, message = "Error occurred."),
        @ApiResponse(code = 401, message = "Unauthorized."),
        @ApiResponse(code = 500, message = "Error.")
})
public class RoleGroupController extends AbstractController {
    private RoleGroupService roleGroupServiceImpl = new RoleGroupServiceImpl();
    private RoleGroupListSearch roleGroupListSearch = new RoleGroupListSearch();
    private RoleGroupSearch roleGroupSearch = new RoleGroupSearch();

    @POST
    @ApiOperation(value = "Create role group.", httpMethod = "POST", response = CreationResponse.class)
    public Response createRoleGroup(@Context HttpHeaders httpHeaders,
                                    @ApiParam @Valid RoleGroupCreateRequest api) {
        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processResponse(roleGroupServiceImpl.createRoleGroup(api));
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processResponse(roleGroupServiceImpl.createRoleGroup(api));
        }

        return Response.status(Response.Status.UNAUTHORIZED).entity("{}").build();
    }

    @PUT
    @ApiOperation(value = "Update role group.", httpMethod = "PUT", response = UpdateResponse.class)
    public Response updateRoleGroup(@Context HttpHeaders httpHeaders,
                                    @ApiParam @Valid RoleGroupUpdateRequest api) {
        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processResponse(roleGroupServiceImpl.updateRoleGroup(api));
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processResponse(roleGroupServiceImpl.updateRoleGroup(api));
        }

        return Response.status(Response.Status.UNAUTHORIZED).entity("{}").build();
    }

    @Path("approve")
    @PUT
    @ApiOperation(value = "Approve role group.", httpMethod = "PUT", response = UpdateResponse.class)
    public Response approveRoleGroup(@Context HttpHeaders httpHeaders,
                                     @ApiParam @Valid StatusUpdateRequest api) {
        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processResponse(roleGroupServiceImpl.approveRoleGroup(api));
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processResponse(roleGroupServiceImpl.approveRoleGroup(api));
        }

        return Response.status(Response.Status.UNAUTHORIZED).entity("{}").build();
    }

    @Path("suspend")
    @PUT
    @ApiOperation(value = "Suspend role group.", httpMethod = "PUT", response = UpdateResponse.class)
    public Response suspendRoleGroup(@Context HttpHeaders httpHeaders,
                                     @ApiParam @Valid StatusUpdateRequest api) {
        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processResponse(roleGroupServiceImpl.suspendRoleGroup(api));
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processResponse(roleGroupServiceImpl.suspendRoleGroup(api));
        }

        return Response.status(Response.Status.UNAUTHORIZED).entity("{}").build();
    }

    @Path("delete")
    @PUT
    @ApiOperation(value = "Delete role group.", httpMethod = "PUT", response = UpdateResponse.class)
    public Response deleteRoleGroup(@Context HttpHeaders httpHeaders,
                                    @ApiParam @Valid StatusUpdateRequest api) {
        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processResponse(roleGroupServiceImpl.deleteRoleGroup(api));
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processResponse(roleGroupServiceImpl.deleteRoleGroup(api));
        }

        return Response.status(Response.Status.UNAUTHORIZED).entity("{}").build();
    }

    @Path("approved_role_groups")
    @GET
    @ApiOperation(value = "Get Approved role list.", httpMethod = "GET", response = UpdateResponse.class)
    public Response getRoleGroupList(@Context HttpHeaders httpHeaders) {
        SearchCriteria api = new SearchCriteria();

        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processDataSearchResponse(roleGroupListSearch.findByCriteria(api));
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processDataSearchResponse(roleGroupListSearch.findByCriteria(api));
        }

        return Response.status(Response.Status.UNAUTHORIZED).entity("{}").build();
    }

    @Path("find_by_criteria")
    @POST
    @ApiOperation(value = "Find role groups by criteria.", httpMethod = "POST", response = UpdateResponse.class)
    public Response findRoleGroupsByCriteria(@Context HttpHeaders httpHeaders,
                                             @ApiParam @Valid SearchCriteria api) {

        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processSearchResponse(roleGroupSearch.findByCriteria(api));
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processSearchResponse(roleGroupSearch.findByCriteria(api));
        }

        return Response.status(Response.Status.UNAUTHORIZED).entity("{}").build();
    }

    @Path("roles")
    @POST
    @ApiOperation(value = "Get role group roles by role group id.", httpMethod = "POST", response = UpdateResponse.class)
    public Response getRoleGroupRolesByRoleGroupId(@Context HttpHeaders httpHeaders,
                                                   @ApiParam @Valid RoleGroupRolesFindRequest api) {
        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processResponse(roleGroupServiceImpl.getRoleGroupRolesByRoleGroupId(api));
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processResponse(roleGroupServiceImpl.getRoleGroupRolesByRoleGroupId(api));
        }

        return Response.status(Response.Status.UNAUTHORIZED).entity("{}").build();
    }
}
