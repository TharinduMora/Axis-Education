package com.teamtrace.axiseducation;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class AxisEducationExceptionMapper implements ExceptionMapper<Exception> {

    public Response toResponse(Exception exception) {
        exception.printStackTrace();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error occurred. Please retry the action. If problem continues please contact admin.")
                .type(MediaType.TEXT_PLAIN)
                .build();
    }
}
