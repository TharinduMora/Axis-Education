package com.teamtrace.axiseducation.service;

import com.teamtrace.axiseducation.api.request.RoleGroupCreateRequest;
import com.teamtrace.axiseducation.api.request.RoleGroupRolesFindRequest;
import com.teamtrace.axiseducation.api.request.RoleGroupUpdateRequest;
import com.teamtrace.axiseducation.api.request.StatusUpdateRequest;
import com.teamtrace.axiseducation.api.response.CreationResponse;
import com.teamtrace.axiseducation.api.response.UpdateResponse;

public interface RoleGroupService {
    CreationResponse createRoleGroup(RoleGroupCreateRequest request);

    UpdateResponse updateRoleGroup(RoleGroupUpdateRequest request);

    UpdateResponse approveRoleGroup(StatusUpdateRequest request);

    UpdateResponse suspendRoleGroup(StatusUpdateRequest request);

    UpdateResponse deleteRoleGroup(StatusUpdateRequest request);

    CreationResponse getRoleGroupRolesByRoleGroupId(RoleGroupRolesFindRequest request);
}
