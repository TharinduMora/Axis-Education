package com.teamtrace.axiseducation.controller;

import com.teamtrace.axiseducation.AxisEducationConfiguration;
import com.teamtrace.axiseducation.api.request.Request;
import com.teamtrace.axiseducation.api.response.FileUploadResponse;
import com.teamtrace.axiseducation.service.FileService;
import com.teamtrace.axiseducation.service.impl.FileServiceImpl;
import com.teamtrace.axiseducation.util.constant.Statuses;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

@Path("file_service")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)
@Api(
        value = "Image management services.",
        consumes = MediaType.APPLICATION_JSON,
        produces = MediaType.APPLICATION_JSON
)
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Request successful."),
        @ApiResponse(code = 204, message = "No data found."),
        @ApiResponse(code = 304, message = "Error occurred."),
        @ApiResponse(code = 401, message = "Unauthorized."),
        @ApiResponse(code = 500, message = "Error.")
})
public class FileController extends AbstractController {
    private FileService fileServiceImpl;

    public FileController(AxisEducationConfiguration axisEducationConfiguration) {
        fileServiceImpl = new FileServiceImpl(axisEducationConfiguration);
    }

    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @ApiOperation(
            value = "Upload image.",
            httpMethod = "POST",
            response = FileUploadResponse.class
    )
    public Response uploadFile(@Context HttpHeaders httpHeaders,
                               @FormDataParam("file") final InputStream fileInputStream,
                               @FormDataParam("shopId") final int shopId, @FormDataParam("type") final String type) {
        Request request = new Request();

        if (isAuthorizedAdminUser(httpHeaders, request)) {
            FileUploadResponse response = fileServiceImpl.uploadImage(fileInputStream, type, request);
            if (response.getStatus() == Statuses.RESPONSE_STATUS_SUCCESS) {
                return Response.ok(response).build();
            } else {
                return Response.notModified().build();
            }
        } else if (isAuthorizedAdminUser(httpHeaders, request)) {
            FileUploadResponse response = fileServiceImpl.uploadImage(fileInputStream, type, request);
            if (response.getStatus() == Statuses.RESPONSE_STATUS_SUCCESS) {
                return Response.ok(response).build();
            } else {
                return Response.notModified().build();
            }
        }

        return Response.status(Response.Status.UNAUTHORIZED).entity("{}").build();
    }

    /*@RequestMapping(value = "delete", method = RequestMethod.POST)
    public ResponseEntity<?> deleteImage(@RequestHeader HttpHeaders httpHeaders, @RequestBody FileDeleteRequest api,
                                         HttpServletResponse servletResponse) {
        if (!isAuthorizedSystemOwner(httpHeaders, api)) {
            servletResponse.setHeader(ETAG, api.getResourceBundle().getString("user_not_authorized"));
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        return processResponse(fileServiceImpl.deleteImageS3(api), servletResponse);
    }*/
}
