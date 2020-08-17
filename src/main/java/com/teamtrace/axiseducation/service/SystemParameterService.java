package com.teamtrace.axiseducation.service;

import com.teamtrace.axiseducation.api.request.SystemParameterCreateRequest;
import com.teamtrace.axiseducation.api.request.SystemParameterUpdateRequest;
import com.teamtrace.axiseducation.api.response.CreationResponse;
import com.teamtrace.axiseducation.api.response.UpdateResponse;

public interface SystemParameterService {
    CreationResponse createSystemParameter(SystemParameterCreateRequest request);

    UpdateResponse updateSystemParameter(SystemParameterUpdateRequest request);
}
