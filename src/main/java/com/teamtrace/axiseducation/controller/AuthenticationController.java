package com.teamtrace.axiseducation.controller;

import com.teamtrace.axiseducation.api.request.AdminUserChangePasswordRequest;
import com.teamtrace.axiseducation.api.request.AdminUserLoginRequest;
import com.teamtrace.axiseducation.api.request.AdminUserLogoutRequest;
import com.teamtrace.axiseducation.api.request.AdminUserResetPasswordRequest;
import com.teamtrace.axiseducation.api.response.CreationResponse;
import com.teamtrace.axiseducation.api.response.UpdateResponse;
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

@Path("authentications")
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
public class AuthenticationController extends AbstractController {
    private AdminUserService adminUserServiceImpl = new AdminUserServiceImpl();

    @Path("change_password")
    @PUT
    @ApiOperation(value = "Change admin user password.", httpMethod = "PUT", response = CreationResponse.class)
    public Response changeAdminUserPassword(@Context HttpHeaders httpHeaders,
                                            @ApiParam @Valid AdminUserChangePasswordRequest api) {
        if (isAuthorizedSystemOwner(httpHeaders, api)) {
            return processResponse(adminUserServiceImpl.changeAdminUserPassword(api));
        } else if (isAuthorizedAdminUser(httpHeaders, api)) {
            return processResponse(adminUserServiceImpl.changeAdminUserPassword(api));
        }

        return Response.status(Response.Status.UNAUTHORIZED).entity("{}").build();
    }

    @Path("login")
    @POST
    @ApiOperation(value = "Admin user login.", httpMethod = "POST", response = CreationResponse.class)
    public Response loginAdminUser(@ApiParam @Valid AdminUserLoginRequest api) {

        return processResponse(adminUserServiceImpl.loginAdminUser(api));
    }

    @PUT
    @ApiOperation(value = "Admin user logout.", httpMethod = "PUT", response = CreationResponse.class)
    public Response logoutSystemUser(@Context HttpHeaders httpHeaders) {
        AdminUserLogoutRequest api = new AdminUserLogoutRequest();
        if (httpHeaders != null && httpHeaders.getRequestHeader("sessionId") != null) {
            api.setSessionId(httpHeaders.getRequestHeader("sessionId").get(0));
        }

        return processResponse(adminUserServiceImpl.logoutAdminUser(api));
    }

    @Path("reset_password")
    @PUT
    @ApiOperation(value = "Reset admin user password.", httpMethod = "PUT", response = UpdateResponse.class)
    public Response resetAdminUserPassword(@Context HttpHeaders httpHeaders,
                                           @ApiParam @Valid AdminUserResetPasswordRequest api) throws GeneralSecurityException {
        return processResponse(adminUserServiceImpl.resetAdminUserPassword(api));
    }
}
