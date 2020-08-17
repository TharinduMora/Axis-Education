package com.teamtrace.axiseducation.service;

import com.teamtrace.axiseducation.api.request.*;
import com.teamtrace.axiseducation.api.response.AdminUserLoginResponse;
import com.teamtrace.axiseducation.api.response.ApiSearchResponse;
import com.teamtrace.axiseducation.api.response.CreationResponse;
import com.teamtrace.axiseducation.api.response.UpdateResponse;

import java.security.GeneralSecurityException;

public interface AdminUserService {
    CreationResponse createAdminUser(AdminUserCreateRequest request) throws GeneralSecurityException;

    UpdateResponse updateAdminUser(AdminUserUpdateRequest request);

    UpdateResponse updateAdminUserBasicDetails(AdminUserBasicDetailsUpdateRequest request);

    UpdateResponse updateAdminUserProfileImage(AdminUserProfileImageUpdateRequest request);

    ApiSearchResponse getAdminUserByUserId(GetByPrimaryIdRequest request);

    UpdateResponse changeAdminUserPassword(AdminUserChangePasswordRequest request);

    UpdateResponse approveAdminUser(StatusUpdateRequest request);

    UpdateResponse suspendAdminUser(StatusUpdateRequest request);

    UpdateResponse deleteAdminUser(StatusUpdateRequest request);

    AdminUserLoginResponse loginAdminUser(AdminUserLoginRequest request);

    UpdateResponse logoutAdminUser(AdminUserLogoutRequest request);

    UpdateResponse resetAdminUserPassword(AdminUserResetPasswordRequest request) throws GeneralSecurityException;

    UpdateResponse resetAdminUserPasswordFormAdmin(AdminUserResetPasswordFromAdminRequest request) throws GeneralSecurityException;

}
