package com.teamtrace.axiseducation.service;

import com.teamtrace.axiseducation.api.request.Request;
import com.teamtrace.axiseducation.api.response.ApiSearchResponse;

public interface RoleService {
    ApiSearchResponse getApprovedRolesByMerchantId(Request request);
}
