package com.teamtrace.axiseducation.controller;

import com.teamtrace.axiseducation.api.request.Request;
import com.teamtrace.axiseducation.api.response.ApiResponse;
import com.teamtrace.axiseducation.api.response.SearchResponse;
import com.teamtrace.axiseducation.model.AdminUser;
import com.teamtrace.axiseducation.util.SessionUtil;
import com.teamtrace.axiseducation.util.constant.Statuses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

public abstract class AbstractController {
    protected static final String ETAG = "ETag";
    private static Logger logger = LoggerFactory.getLogger(AbstractController.class);

    protected boolean isAuthorizedSystemOwner(HttpHeaders httpHeaders, Request request) {
        if (httpHeaders == null || httpHeaders.getRequestHeader("sessionId") == null) {
            return false;
        }
        String sessionId = httpHeaders.getRequestHeader("sessionId").get(0);
        AdminUser adminUser = SessionUtil.getAdminUserSession(sessionId);

        if (adminUser == null) {
            logger.info("Try to access without valid session. Session : {}", sessionId);
            return false;
        }
        if (adminUser.getStatus() != Statuses.APPROVED) {
            logger.info("Un-approved admin. Session : {}", sessionId);
            return false;
        }
        //todo check system owner

        request.setAdminUser(adminUser);

        return true;
    }

    protected boolean isAuthorizedAdminUser(HttpHeaders httpHeaders, Request request) {
        if (httpHeaders == null || httpHeaders.getRequestHeader("sessionId") == null) {
            return false;
        }
        String sessionId = httpHeaders.getRequestHeader("sessionId").get(0);
        AdminUser adminUser = SessionUtil.getAdminUserSession(sessionId);

        if (adminUser == null) {
            logger.info("Try to access without valid session. Session : {}", sessionId);
            return false;
        }

        if (adminUser.getStatus() != Statuses.APPROVED) {
            logger.info("Un-approved admin. Session : {}", sessionId);
            return false;
        }

        request.setAdminUser(adminUser);

        return true;
    }

    protected Response processResponse(ApiResponse response) {
        if (response.getStatus() == Statuses.RESPONSE_STATUS_FAIL) {
            return Response.notModified(response.getMessage()).build();
        } else {
            return Response.ok(response).build();
        }
    }

    protected Response processSearchResponse(SearchResponse response) {
        switch (response.getStatus()) {
            case Statuses.RESPONSE_STATUS_SUCCESS: {
                if (response.getData() == null) {
                    return Response.noContent().build();
                } else if (response.getData().isEmpty()) {
                    return Response.noContent().build();
                } else {
                    return Response.ok(response).build();
                }
            }
            case Statuses.RESPONSE_STATUS_NO_DATA: {
                return Response.noContent().build();
            }
            case Statuses.RESPONSE_STATUS_FAIL: {
                return Response.notModified(response.getMessage()).build();
            }
            default: {
                return Response.notModified(response.getMessage()).build();
            }
        }
    }

    protected Response processDataSearchResponse(SearchResponse searchResponse) {
        switch (searchResponse.getStatus()) {
            case Statuses.RESPONSE_STATUS_SUCCESS: {
                if (searchResponse.getData() == null || searchResponse.getData().isEmpty()) {
                    return Response.noContent().build();
                } else {
                    return Response.ok(searchResponse).build();
                }
            }
            case Statuses.RESPONSE_STATUS_NO_DATA: {
                return Response.noContent().build();
            }
            case Statuses.RESPONSE_STATUS_FAIL: {
                return Response.notModified(searchResponse.getMessage()).build();
            }
            default: {
                return Response.notModified(searchResponse.getMessage()).build();
            }
        }
    }
}
