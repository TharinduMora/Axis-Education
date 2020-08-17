package com.teamtrace.axiseducation.controller;

import com.teamtrace.axiseducation.api.request.*;
import com.teamtrace.axiseducation.api.response.CreationResponse;
import com.teamtrace.axiseducation.api.response.UpdateResponse;
import com.teamtrace.axiseducation.search.adminuser.AdminUserSearch;
import com.teamtrace.axiseducation.service.AdminUserService;
import com.teamtrace.axiseducation.service.impl.AdminUserServiceImpl;
import io.swagger.annotations.*;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.GeneralSecurityException;

@Path("admin_service")
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
public class AdminUserController extends AbstractController {

    private AdminUserService adminUserServiceImpl = new AdminUserServiceImpl();
    private AdminUserSearch adminUserSearch = new AdminUserSearch();

    @POST
    @ApiOperation(value = "Create admin user.", httpMethod = "POST", response = CreationResponse.class)
    public Response createAdminUser(@Context HttpHeaders httpHeaders,
                                    @ApiParam @Valid AdminUserCreateRequest api) throws GeneralSecurityException {
        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processResponse(adminUserServiceImpl.createAdminUser(api));
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processResponse(adminUserServiceImpl.createAdminUser(api));
        }

        return Response.status(Response.Status.UNAUTHORIZED).entity("{}").build();
    }

    @PUT
    @ApiOperation(value = "Create admin user.", httpMethod = "PUT", response = UpdateResponse.class)
    public Response updateAdminUser(@Context HttpHeaders httpHeaders,
                                    @ApiParam @Valid AdminUserUpdateRequest api) {
        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processResponse(adminUserServiceImpl.updateAdminUser(api));
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processResponse(adminUserServiceImpl.updateAdminUser(api));
        }

        return Response.status(Response.Status.UNAUTHORIZED).entity("{}").build();
    }

    @Path("basic_details")
    @PUT
    @ApiOperation(value = "Create admin user.", httpMethod = "PUT", response = UpdateResponse.class)
    public Response updateAdminUserBasicDetails(@Context HttpHeaders httpHeaders,
                                                @ApiParam @Valid AdminUserBasicDetailsUpdateRequest api) {
        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processResponse(adminUserServiceImpl.updateAdminUserBasicDetails(api));
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processResponse(adminUserServiceImpl.updateAdminUserBasicDetails(api));
        }

        return Response.status(Response.Status.UNAUTHORIZED).entity("{}").build();
    }

    @Path("profile_image")
    @PUT
    @ApiOperation(value = "Create admin user.", httpMethod = "PUT", response = UpdateResponse.class)
    public Response updateAdminUserProfileImage(@Context HttpHeaders httpHeaders,
                                                @ApiParam @Valid AdminUserProfileImageUpdateRequest api) {
        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processResponse(adminUserServiceImpl.updateAdminUserProfileImage(api));
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processResponse(adminUserServiceImpl.updateAdminUserProfileImage(api));
        }

        return Response.status(Response.Status.UNAUTHORIZED).entity("{}").build();
    }

    @Path("admin_user_id/{adminUserId}")
    @PUT
    @ApiOperation(value = "Create admin user.", httpMethod = "PUT", response = UpdateResponse.class)
    public Response getAdminUserUserId(@Context HttpHeaders httpHeaders,
                                       @ApiParam @PathParam("adminUserId") int adminUserId) {
        GetByPrimaryIdRequest request = new GetByPrimaryIdRequest();
        request.setPrimaryId(adminUserId);

        if (isAuthorizedSystemOwner(httpHeaders, request)) {
            return processResponse(adminUserServiceImpl.getAdminUserByUserId(request));
        } else if (isAuthorizedAdminUser(httpHeaders, request)) {
            return processResponse(adminUserServiceImpl.getAdminUserByUserId(request));
        }

        return Response.status(Response.Status.UNAUTHORIZED).entity("{}").build();
    }

    @Path("approve")
    @PUT
    @ApiOperation(value = "Create admin user.", httpMethod = "PUT", response = UpdateResponse.class)
    public Response approveAdminUser(@Context HttpHeaders httpHeaders,
                                     @ApiParam @Valid StatusUpdateRequest api) {
        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processResponse(adminUserServiceImpl.approveAdminUser(api));
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processResponse(adminUserServiceImpl.approveAdminUser(api));
        }

        return Response.status(Response.Status.UNAUTHORIZED).entity("{}").build();
    }

    @Path("suspend")
    @PUT
    @ApiOperation(value = "Create admin user.", httpMethod = "PUT", response = UpdateResponse.class)
    public Response suspendAdminUser(@Context HttpHeaders httpHeaders,
                                     @ApiParam @Valid StatusUpdateRequest api) {
        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processResponse(adminUserServiceImpl.suspendAdminUser(api));
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processResponse(adminUserServiceImpl.suspendAdminUser(api));
        }

        return Response.status(Response.Status.UNAUTHORIZED).entity("{}").build();
    }

    @Path("delete")
    @PUT
    @ApiOperation(value = "Create admin user.", httpMethod = "PUT", response = UpdateResponse.class)
    public Response deleteAdminUser(@Context HttpHeaders httpHeaders,
                                    @ApiParam @Valid StatusUpdateRequest api) {
        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processResponse(adminUserServiceImpl.deleteAdminUser(api));
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processResponse(adminUserServiceImpl.deleteAdminUser(api));
        }

        return Response.status(Response.Status.UNAUTHORIZED).entity("{}").build();
    }

    @Path("find_by_criteria")
    @POST
    @ApiOperation(value = "Create admin user.", httpMethod = "POST", response = UpdateResponse.class)
    public Response findAdminUsersByCriteria(@Context HttpHeaders httpHeaders,
                                             @ApiParam @Valid SearchCriteria api) {

        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processSearchResponse(adminUserSearch.findByCriteria(api));
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processSearchResponse(adminUserSearch.findByCriteria(api));
        }

        return Response.status(Response.Status.UNAUTHORIZED).entity("{}").build();
    }

    @Path("reset_password")
    @POST
    @ApiOperation(value = "Create admin user.", httpMethod = "POST", response = UpdateResponse.class)
    public Response resetAdminUserPasswordFormAdmin(@Context HttpHeaders httpHeaders,
                                                    @ApiParam @Valid AdminUserResetPasswordFromAdminRequest api) throws GeneralSecurityException {

        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processResponse(adminUserServiceImpl.resetAdminUserPasswordFormAdmin(api));
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processResponse(adminUserServiceImpl.resetAdminUserPasswordFormAdmin(api));
        }

        return Response.status(Response.Status.UNAUTHORIZED).entity("{}").build();
    }
}
