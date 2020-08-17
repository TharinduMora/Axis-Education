package com.teamtrace.axiseducation.service;

import com.teamtrace.axiseducation.api.request.SystemParameterCreateRequest;
import com.teamtrace.axiseducation.api.request.SystemParameterUpdateRequest;
import com.teamtrace.axiseducation.api.response.CreationResponse;
import com.teamtrace.axiseducation.api.response.UpdateResponse;

public interface SystemStatementService {
    CreationResponse createSystemStatement(SystemParameterCreateRequest request);

    UpdateResponse updateSystemStatement(SystemParameterUpdateRequest request);
}
