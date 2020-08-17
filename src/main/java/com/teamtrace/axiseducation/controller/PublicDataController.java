package com.teamtrace.axiseducation.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("public_data")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Public data services.", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Request successful."),
        @ApiResponse(code = 204, message = "No data found."),
        @ApiResponse(code = 304, message = "Error occurred."),
        @ApiResponse(code = 401, message = "Unauthorized."),
        @ApiResponse(code = 500, message = "Error.")
})
public class PublicDataController extends AbstractController {

}
